package cl.bci.ecodig.customersupport.customerprofile.helper

import cl.bci.ecodig.customersupport.customerprofile.controller.dto.products.CustomersProductsResponseDTO
import cl.bci.ecodig.customersupport.customerprofile.controller.dto.products.ProductsInfoDTO
import cl.bci.ecodig.customersupport.customerprofile.controller.dto.profile.CustomersProfileResponseDTO
import cl.bci.ecodig.customersupport.customerprofile.controller.dto.profile.PartnersDTO

class ProfileFakeData {


    static PartnersDTO getPartnerBci(){
        return PartnersDTO.builder().id(1).partnerName("Bci").build()
    }

    static PartnersDTO getPartnerMach(){
        return PartnersDTO.builder().id(2).partnerName("MACH").build()
    }

    static CustomersProfileResponseDTO getProfileResponse(){
        return CustomersProfileResponseDTO.builder()
                .id("123")
                .documentNumber("1-9")
                .active(true)
                .partners(Arrays.asList(getPartnerBci(),getPartnerMach())).build()
    }

    static CustomersProfileResponseDTO getProfileResponseJustBci(){
        return CustomersProfileResponseDTO.builder()
                .id("123")
                .documentNumber("1-9")
                .active(true)
                .partners(Arrays.asList(getPartnerBci())).build()
    }

    static ProductsInfoDTO getBciPlusProduct(){
        ProductsInfoDTO.builder().id(1).name("BciPlus+").build()
    }

    static CustomersProductsResponseDTO getProductsResponse(){
        return CustomersProductsResponseDTO.builder()
                .products(Arrays.asList(getBciPlusProduct())).build()
    }

    static CustomersProductsResponseDTO getProductsEmptyResponse(){
        return CustomersProductsResponseDTO.builder()
                .products(new ArrayList<ProductsInfoDTO>()).build()
    }

}
