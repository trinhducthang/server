package com.kachina.company_service.mapper;

import com.kachina.company_service.dto.request.CompanyRequest;
import com.kachina.company_service.dto.response.CompanyResponse;
import com.kachina.company_service.entity.Company;
import lombok.RequiredArgsConstructor;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    public Company toCompany(CompanyRequest request) {
        return Company.builder()
                .name(CharacterReference.encode(request.getName()))
                .logo_url("")
                .email(request.getEmail())
                .phone(request.getPhone())
                .size(request.getSize())
                .address(CharacterReference.encode(request.getAddress()))
                .description(CharacterReference.encode(request.getDescription()))
                .business_type(request.getBusiness_type())
                .authorId("")
                .fields(request.getFields())
                .cover_photo("")
                .tax_code(request.getTax_code())
                .website(request.getWebsite())
                .build();
    }

    public Company toCompany(Company company, String authorId) {
        return Company.builder()
                .name(company.getName())
                .logo_url(company.getLogo_url())
                .email(company.getEmail())
                .phone(company.getPhone())
                .size(company.getSize())
                .address(company.getAddress())
                .description(company.getDescription())
                .business_type(company.getBusiness_type())
                .authorId(authorId)
                .fields(company.getFields())
                .cover_photo(company.getCover_photo())
                .tax_code(company.getTax_code())
                .website(company.getWebsite())
                .build();
    }

    public CompanyResponse toCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .logo_url(company.getLogo_url())
                .fields(company.getFields())
                .address(CharacterReference.decode(company.getAddress()))
                .phone(company.getPhone())
                .size(company.getSize())
                .email(company.getEmail())
                .tax_code(company.getTax_code())
                .website(company.getWebsite())
                .cover_photo(company.getCover_photo())
                .business_type(company.getBusiness_type())
                .name(CharacterReference.decode(company.getName()))
                .build();
    }

}
