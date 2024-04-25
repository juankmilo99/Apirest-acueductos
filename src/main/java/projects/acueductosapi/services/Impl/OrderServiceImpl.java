package projects.acueductosapi.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.acueductosapi.entities.Order;
import projects.acueductosapi.entities.OrderItem;
import projects.acueductosapi.entities.Product;
import projects.acueductosapi.repository.OrderItemRepository;
import projects.acueductosapi.repository.OrderRepository;
import projects.acueductosapi.repository.ProductoRepository;
import projects.acueductosapi.response.OrderResponseRest;
import projects.acueductosapi.services.OrderService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductoRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;



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
    @Transactional(readOnly = true)
    public ResponseEntity<OrderResponseRest> buscarOrderPorId(Integer orderId) {
        OrderResponseRest response = new OrderResponseRest();
        List<Order> list = new ArrayList<>();

        try {
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                // Convertir la imagen en Base64 para cada producto en la orden
                for (OrderItem item : order.get().getItems()) {
                    byte[] imageBytes = item.getProduct().getImage();
                    String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                    item.getProduct().setImageBase64(imageBase64);
                }

                list.add(order.get());
                response.getOrderResponse().setOrders(list);
                response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK);
            } else {
                response.setMetadata("Respuesta nok", "-1", "Order no encontrada");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar Order");
            log.error("error al consultar Order: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        /*
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

        } catch (MessagingException e) {
            // Manejo de errores si falla el envío del correo electrónico
            log.error("Error al enviar correo electrónico: " + e.getMessage());
        }
        */




        response.setMetadata("Respuesta ok", "00", "Producto creado");
        return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<OrderResponseRest> addToCart(Integer orderId, Integer productId, Integer quantity) {
        OrderResponseRest response = new OrderResponseRest();

        try {
            Order order = orderRepository.findById(orderId).orElse(null);
            Product product = productRepository.findById(productId).orElse(null);

            if (order == null || product == null) {
                response.setMetadata("Respuesta nok", "-1", "Orden o producto no encontrado");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            OrderItem item = order.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (item == null) {
                // Si el producto no está en el carrito, crear un nuevo item
                item = new OrderItem();
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(quantity);

                orderItemRepository.save(item);
                order.getItems().add(item);
            } else {
                // Si el producto ya está en el carrito, aumentar la cantidad
                item.setQuantity(item.getQuantity() + quantity);
            }

            // Actualizar el precio total de la orden
            BigDecimal itemTotalPrice = product.getPrice().multiply(new BigDecimal(quantity));
            order.setTotalPrice(order.getTotalPrice().add(itemTotalPrice));

            orderRepository.save(order);

            response.setMetadata("Respuesta ok", "00", "Producto agregado al carrito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al agregar producto al carrito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<OrderResponseRest> increaseItemQuantity(Integer orderId, Integer productId, Integer quantity) {
        OrderResponseRest response = new OrderResponseRest();

        try {
            Order order = orderRepository.findById(orderId).orElse(null);
            Product product = productRepository.findById(productId).orElse(null);

            if (order == null || product == null) {
                response.setMetadata("Respuesta nok", "-1", "Orden o producto no encontrado");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            OrderItem item = order.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (item == null) {
                response.setMetadata("Respuesta nok", "-1", "Producto no encontrado en el carrito");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Aumentar la cantidad del producto en el carrito
            item.setQuantity(item.getQuantity() + quantity);

            // Actualizar el precio total de la orden
            BigDecimal itemTotalPrice = product.getPrice().multiply(new BigDecimal(quantity));
            order.setTotalPrice(order.getTotalPrice().add(itemTotalPrice));

            orderRepository.save(order);

            response.setMetadata("Respuesta ok", "00", "Cantidad de producto aumentada en el carrito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al aumentar la cantidad de producto en el carrito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<OrderResponseRest> decreaseItemQuantity(Integer orderId, Integer productId, Integer quantity) {
        OrderResponseRest response = new OrderResponseRest();

        try {
            Order order = orderRepository.findById(orderId).orElse(null);
            Product product = productRepository.findById(productId).orElse(null);

            if (order == null || product == null) {
                response.setMetadata("Respuesta nok", "-1", "Orden o producto no encontrado");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            OrderItem item = order.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (item == null) {
                response.setMetadata("Respuesta nok", "-1", "Producto no encontrado en el carrito");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Disminuir la cantidad del producto en el carrito
            int newQuantity = item.getQuantity() - quantity;
            if (newQuantity <= 0) {
                // Si la cantidad es 0 o menos, eliminar el producto del carrito
                order.getItems().remove(item);
                orderItemRepository.delete(item);
            } else {
                item.setQuantity(newQuantity);
            }

            // Actualizar el precio total de la orden
            BigDecimal itemTotalPrice = product.getPrice().multiply(new BigDecimal(quantity));
            order.setTotalPrice(order.getTotalPrice().subtract(itemTotalPrice));

            orderRepository.save(order);

            response.setMetadata("Respuesta ok", "00", "Cantidad de producto disminuida en el carrito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al disminuir la cantidad de producto en el carrito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    @Transactional
    public ResponseEntity<OrderResponseRest> updateItemQuantity(Integer orderId, Integer productId, Integer quantity) {
        OrderResponseRest response = new OrderResponseRest();

        try {
            Order order = orderRepository.findById(orderId).orElse(null);
            Product product = productRepository.findById(productId).orElse(null);

            if (order == null || product == null) {
                response.setMetadata("Respuesta nok", "-1", "Orden o producto no encontrado");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            OrderItem item = order.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (item == null) {
                response.setMetadata("Respuesta nok", "-1", "Producto no encontrado en el carrito");
                return new ResponseEntity<OrderResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Calcular la diferencia en la cantidad del producto
            int quantityDifference = quantity - item.getQuantity();

            // Si la nueva cantidad es 0, eliminar el producto del carrito
            if (quantity == 0) {
                order.getItems().remove(item);
                orderItemRepository.delete(item);
            } else {
                // Actualizar la cantidad del producto en el carrito
                item.setQuantity(quantity);
            }

            // Actualizar el precio total de la orden
            BigDecimal itemTotalPriceDifference = product.getPrice().multiply(new BigDecimal(quantityDifference));
            order.setTotalPrice(order.getTotalPrice().add(itemTotalPriceDifference));

            orderRepository.save(order);

            response.setMetadata("Respuesta ok", "00", "Cantidad de producto actualizada en el carrito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al actualizar la cantidad de producto en el carrito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}







    @Transactional
    public ResponseEntity<OrderResponseRest> sendEmail(Map<String, String> emailData) {
        OrderResponseRest response = new OrderResponseRest();

        try {
            String email = emailData.get("email");
            String subject = emailData.get("subject");
            String body = emailData.get("body");

            enviarCorreo(email, subject, body);

            response.setMetadata("Respuesta ok", "00", "Correo electrónico enviado con éxito");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al enviar el correo electrónico");
            return new ResponseEntity<OrderResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException {
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
        message.setSubject(asunto); // Asunto del correo electrónico
        message.setContent(cuerpo, "text/html"); // Contenido del correo electrónico

        // Envío del mensaje de correo electrónico
        Transport.send(message);
    }
}
