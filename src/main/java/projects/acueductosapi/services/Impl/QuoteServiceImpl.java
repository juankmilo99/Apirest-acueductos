package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Quote;
import projects.acueductosapi.repository.QuoteRepository;
import projects.acueductosapi.repository.UsuarioRepository;
import projects.acueductosapi.response.QuoteResponseRest;
import projects.acueductosapi.services.QuoteService;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class QuoteServiceImpl implements QuoteService {
    private static final Logger log = LoggerFactory.getLogger(QuoteServiceImpl.class);

    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<QuoteResponseRest> buscarQuotes() {

        QuoteResponseRest response = new QuoteResponseRest();

        try {
            List<Quote> quotes = quoteRepository.findAll();
            response.getQuoteResponse().setQuotes(quotes);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar Quotes");
            log.error("error al consultar Quotes: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<QuoteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<QuoteResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<QuoteResponseRest> crear(Quote quote) {
        log.info("Inicio metodo crear Quote");

        QuoteResponseRest response = new QuoteResponseRest();
        List<Quote> list = new ArrayList<>();

        try {

            Quote QuoteGuardado = quoteRepository.save(quote);


            if( QuoteGuardado != null) {
                list.add(QuoteGuardado);
                response.getQuoteResponse().setQuotes(list);
            } else {
                log.error("Error en grabar Quote");
                response.setMetadata("Respuesta nok", "-1", "Quote no guardado");
                return new ResponseEntity<QuoteResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch( Exception e) {
            log.error("Error en grabar Quote ");
            response.setMetadata("Respuesta nok", "-1", "Error al grabar Quote");
            return new ResponseEntity<QuoteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            // Enviar correo electrónico con la información de la cotización
            enviarCorreo(quote.getEmail(), quote.getDescription(), quote.getPrice());
        } catch (MessagingException e) {
            // Manejo de errores si falla el envío del correo electrónico
            log.error("Error al enviar correo electrónico: " + e.getMessage());
        }

        response.setMetadata("Respuesta ok", "00", "Quote creado");
        return new ResponseEntity<QuoteResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    private void enviarCorreo(String destinatario, String descripcion, BigDecimal precio) throws MessagingException {
        // Configuración de las propiedades del correo electrónico
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Configura el host SMTP adecuado
        props.put("mail.smtp.port", "587"); // Puerto SMTP

        // Autenticación del remitente del correo electrónico
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("beanmailer63@gmail.com", "uglmvbaqcaiujwxa");
            }
        });

        // Creación del mensaje de correo electrónico
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("beanmailer63@gmail.com")); // Correo electrónico del remitente
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Correo electrónico del destinatario
        message.setSubject("Nueva Cotización"); // Asunto del correo electrónico
        message.setText("Descripción: " + descripcion + "\nPrecio: " + precio); // Contenido del correo electrónico

        // Envío del mensaje de correo electrónico
        Transport.send(message);
    }
}

