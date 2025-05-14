package com.kachina.company_service.mapper;

import com.kachina.company_service.dto.request.CompanyRequest;
import com.kachina.company_service.dto.response.CompanyResponse;
import com.kachina.company_service.entity.Company;
import lombok.RequiredArgsConstructor;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    public Company toCompany(CompanyRequest request) {
        return Company.builder()
                .name(CharacterReference.encode(request.getName()))
                .logoUrl("")
                .email(request.getEmail())
                .phone(request.getPhone())
                .size(request.getSize())
                .address(CharacterReference.encode(request.getAddress()))
                .description(CharacterReference.encode(request.getDescription()))
                .businessType(request.getBusiness_type())
                .authorId("")
                .fields(request.getFields())
                .coverPhoto("")
                .taxCode(request.getTax_code())
                .website(request.getWebsite())
                .build();
    }

    public Company toCompany(Company company, String authorId) {
        return Company.builder()
                .name(company.getName())
                .logoUrl(company.getLogoUrl())
                .email(company.getEmail())
                .phone(company.getPhone())
                .size(company.getSize())
                .address(company.getAddress())
                .description(company.getDescription())
                .businessType(company.getBusinessType())
                .authorId(authorId)
                .fields(company.getFields())
                .coverPhoto(company.getCoverPhoto())
                .taxCode(company.getTaxCode())
                .website(company.getWebsite())
                .build();
    }

    public CompanyResponse toCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .logo_url(company.getLogoUrl())
                .fields(company.getFields())
                .address(CharacterReference.decode(company.getAddress()))
                .phone(company.getPhone())
                .size(company.getSize())
                .email(company.getEmail())
                .tax_code(company.getTaxCode())
                .website(company.getWebsite())
                .cover_photo(company.getCoverPhoto())
                .business_type(company.getBusinessType())
                .name(CharacterReference.decode(company.getName()))
                .description(CharacterReference.decode(company.getDescription()))
                .build();
    }

}
