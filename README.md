```text
 __ __|                  |
    |   _` |  |   |      |   _` | \ \   /  _` |  \ \   /  __ \
    |  (   |  |   |  \   |  (   |  \ \ /  (   |   \ \ /   |   |
   _| \__,_| \__, | \___/  \__,_|   \_/  \__,_| _) \_/   _|  _|
             ____/ Lập Trình Java Từ A-Z
 
   Website: https://tayjava.vn
   Youtube: https://youtube.com/@tayjava 
   TikTok: https://tiktok.com/@tayjava.vn 
```
## Prerequisite
- Cài đặt JDK 17+ nếu chưa thì [cài đặt JDK](https://tayjava.vn/cai-dat-jdk-tren-macos-window-linux-ubuntu/)
- Install Maven 3.5+ nếu chưa thì [cài đặt Maven](https://tayjava.vn/cai-dat-maven-tren-macos-window-linux-ubuntu/)
- Install IntelliJ nếu chưa thì [cài đặt IntelliJ](https://tayjava.vn/cai-dat-intellij-tren-macos-va-window/)

## Technical Stacks
- Java 17
- Maven 3.5+
- Spring Boot 3.2.3
- Spring Data Validation
- Spring Data JPA
- Postgres
- Lombok
- DevTools
- Docker, Docker compose

---

## Guideline: How to generate QR Code and Barcode in Spring Boot

### 1. Add dependency `ZXing Library`

```
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>core</artifactId>
    <version>3.3.0</version>
</dependency>
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>javase</artifactId>
    <version>3.3.0</version>
</dependency>
```

### 2. Init @Bean `BufferedImageHttpMessageConverter`

```
@Configuration
public class AppConfig {

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
```

### 3. Generate Barcode & QR Code Image
- Generate QR code
```
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
@Slf4j(topic = "COMMON-SERVICE")
public class CommonService {

    public BufferedImage generateQRCodeImage(String text) throws WriterException {
        log.info("Generate QR code image: {}", text);

        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public BufferedImage generateBarCodeImage(String barcode) throws WriterException {
        log.info("Generate Bar code image: {}", barcode);

        // TODO validate EAN13

        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcode, BarcodeFormat.EAN_13, 300, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
```

### 4. Building a REST API

```
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.tayjava.dto.response.ResponseData;
import vn.tayjava.dto.response.ResponseError;
import vn.tayjava.service.CommonService;
import vn.tayjava.service.MailService;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestController
@RequestMapping("/common")
public record CommonController(CommonService commonService) {

    @PostMapping(path = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@RequestParam String text) throws Exception {
        log.info("Generate qrcode request");
        return ResponseEntity.ok(commonService.generateQRCodeImage(text));
    }

    @PostMapping(path = "/barcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateBarcode(@RequestParam String barcode) throws Exception {
        log.info("Generate barcode request");
        return ResponseEntity.ok(commonService.generateBarCodeImage(barcode));
    }
}
```

### 5. Test
Finally, we can use Postman or a browser to view the generated barcodes.

- Test Barcodes

```
curl --location --request POST 'http://localhost:8080/common/barcode?barcode=1234567890128'
```

- Test QR code

```
curl --location --request POST 'http://localhost:8080/common/qrcode?text=tayjava'
```
