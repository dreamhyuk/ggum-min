package com.study.myshop.dto.store;

import com.study.myshop.domain.Store;
import com.study.myshop.domain.StoreCategoryMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStoreRequest {

    private Long storeId;

    private String storeName;

//    private MultipartFile imageFile;
    private String imageUrl;

    private String city;
    private String street;
    private String zipcode;

    private List<Long> categoryIds;


    //설명 등이 필요하면
//    private String description;


    public CreateStoreRequest(Store store) {
        this.storeId = store.getId();
        this.storeName = store.getStoreName();
        this.imageUrl = store.getImageUrl();

        if (store.getAddress() != null) {
            this.city = store.getAddress().getCity();
            this.street = store.getAddress().getStreet();
            this.zipcode = store.getAddress().getZipcode();
        }

        this.categoryIds = store.getStoreCategoryMappings().stream()
                .map(mapping -> mapping.getStoreCategory().getId())
                .collect(Collectors.toList());
    }
}
