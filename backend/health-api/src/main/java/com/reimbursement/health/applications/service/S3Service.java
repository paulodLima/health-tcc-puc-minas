package com.reimbursement.health.applications.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(String keyName, InputStream inputStream, long contentLength) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, inputStream, null);
        amazonS3.putObject(putObjectRequest);
        URL fileUrl = amazonS3.getUrl(bucketName, keyName);
        return fileUrl.toString();
    }
    /*
     @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody FileUploadRequest request) {
        // Supondo que você tenha um método para criar um arquivo a partir do conteúdo do request
        File file = createFileFromRequest(request);
        String fileName = request.getFileName(); // Nome do arquivo no S3

        // Fazer upload e obter a URL pública
        String fileUrl = fileService.uploadFile(file, fileName);

        // Salvar informações sobre o arquivo no banco de dados
        UUID reimbursementRequestId = request.getReimbursementRequestId();
        String inclusionUser = request.getInclusionUser();
        fileService.saveInvoice(reimbursementRequestId, fileUrl, inclusionUser);

        return ResponseEntity.ok("File uploaded with URL: " + fileUrl);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) {
        try {
            InputStreamResource resource = fileService.getFileByUrl(fileName);

            // Definir cabeçalhos de resposta
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf"); // Defina o tipo de conteúdo apropriado
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            // Retornar o arquivo como InputStreamResource
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } catch (Exception e) {
            // Tratar erro
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}*/
}
