package propensi.propensiun.abuya.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class SupabaseServiceImpl implements SupabaseService {

    private final RestTemplate restTemplate;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.apiKey}")
    private String supabaseApiKey;

    @Value("${supabase.bucketName}")
    private String bucketName;

    public SupabaseServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String uploadImage(MultipartFile image) throws Exception {
        // Generate a unique filename for the image
        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();

        // Construct the URL to upload the image
        String uploadUrl = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName;

        // Set headers for authorization and content type
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseApiKey);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create a multi-part form to send the image file
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", image.getResource());

        // Prepare the HTTP entity for the request
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // Send the POST request to Supabase to upload the image
        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, entity, String.class);

        // Log the response for debugging purposes
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        // If the request is successful, return the URL to the uploaded file
        if (response.getStatusCode() == HttpStatus.OK) {
            return supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + fileName;
        } else {
            throw new RuntimeException("Error uploading image to Supabase: " + response.getStatusCode());
        }
    }
}

