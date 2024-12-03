package propensi.propensiun.abuya.service;

import org.springframework.web.multipart.MultipartFile;

public interface SupabaseService {

    // Method untuk upload gambar ke Supabase
    String uploadImage(MultipartFile image) throws Exception;
}
