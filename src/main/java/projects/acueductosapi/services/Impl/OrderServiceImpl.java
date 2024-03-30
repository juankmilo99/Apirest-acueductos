package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Order;
import projects.acueductosapi.entities.User;
import projects.acueductosapi.repository.OrderRepository;
import projects.acueductosapi.repository.UsuarioRepository;
import projects.acueductosapi.response.OrderResponseRest;
import projects.acueductosapi.services.OrderService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<OrderResponseRest> buscarOrders() {

        OrderResponseRest response = new OrderResponseRest();

        try {
            List<Order> orders = orderRepository.findAll();
            response.getOrderResponse().setOrders(orders);
            response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
        } catch (Exception e) {

            response.setMetadata("Respuesta nok", "-1", "Error al consultar Quotes");
            log.error("error al consultar Quotes: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<OrderResponseRest> crear(Order order) {
        log.info("Inicio metodo crear Order");

        OrderResponseRest response = new OrderResponseRest();
        List<Order> list = new ArrayList<>();

        try {

            Order OrderGuardada = orderRepository.save(order);

            if( OrderGuardada != null) {
                list.add(OrderGuardada);
                response.getOrderResponse().setOrders(list);
            } else {
                log.error("Error en grabar Orden");
                response.setMetadata("Respuesta nok", "-1", "Orden no guardado");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch( Exception e) {
            log.error("Error en grabar Orden ");
            response.setMetadata("Respuesta nok", "-1", "Error al grabar Orden");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            // Recuperar los detalles del usuario asociado a la orden
            User user = usuarioRepository.findById(order.getUser_id()).orElse(null);
            if (user == null) {
                log.error("Error: Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Obtener la información relevante del usuario
            String destinatario = user.getEmail(); // Dirección de correo electrónico del usuario

            // Obtener los detalles de la orden
            String descripcion = "ID del producto: " + order.getProduct_id() + "\nCantidad: " + order.getQuantity();
            BigDecimal precio = order.getTotal_price();

            // Enviar el correo electrónico al usuario
            enviarCorreo(destinatario, descripcion, precio);
        } catch (MessagingException e) {
            // Manejo de errores si falla el envío del correo electrónico
            log.error("Error al enviar correo electrónico: " + e.getMessage());
        }

        response.setMetadata("Respuesta ok", "00", "Producto creado");
        return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK); //devuelve 200
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
        message.setSubject("Nueva Orden"); // Asunto del correo electrónico
        message.setText(descripcion + "\nPrecio: " + precio); // Contenido del correo electrónico

        // Envío del mensaje de correo electrónico
        Transport.send(message);
    }
}
