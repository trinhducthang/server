package com.kachina.company_service.service;

import com.kachina.company_service.dto.request.CompanyRequest;
import com.kachina.company_service.dto.response.ApiResponse;
import com.kachina.company_service.dto.response.CompanyResponse;
import com.kachina.company_service.entity.Company;
import com.kachina.company_service.exception.NotFoundException;
import com.kachina.company_service.helper.AuthHelper;
import com.kachina.company_service.mapper.CompanyMapper;
import com.kachina.company_service.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import net.htmlparser.jericho.CharacterReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AuthHelper authHelper;
    private final CompanyMapper companyMapper;
    private final CloudinaryService cloudinaryService;

    public ResponseEntity<ApiResponse<CompanyResponse>> addCompany(CompanyRequest request) {
        String authorId = authHelper.getCurrentUserId();
        Company company = companyMapper.toCompany(request);
        company.setAuthorId(authorId);
        if(request.getLogo_url() != null && !request.getLogo_url().equals("")) {
            String logo_url = cloudinaryService.uploadBase64Image(request.getLogo_url(), "company_logo_" + authorId);
            company.setLogoUrl(logo_url);
        }
        if(request.getCover_photo() != null && !request.getCover_photo().equals("")) {
            String cover_photo_url = cloudinaryService.uploadBase64Image(request.getCover_photo(), "company_cover_photo_" + authorId);
            company.setCoverPhoto(cover_photo_url);
        }

        company = companyRepository.save(company);

        ApiResponse<CompanyResponse> response = ApiResponse.<CompanyResponse>builder()
                .message("Tạo công ty thành công!")
                .status(HttpStatus.CREATED.value())
                .result(companyMapper.toCompanyResponse(company))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<CompanyResponse>> addCompanyById(String id) {
        String authorId = authHelper.getCurrentUserId();
        System.out.println(id);
        Optional<Company> cOpt = companyRepository.findById(id);
        if(!cOpt.isPresent())  throw new NotFoundException("Không tìm thấy công ty!");
        Company company = companyRepository.save(companyMapper.toCompany(cOpt.get(), authorId));

        ApiResponse<CompanyResponse> response = ApiResponse.<CompanyResponse>builder()
                .message("Tạo công ty thành công!")
                .status(HttpStatus.CREATED.value())
                .result(companyMapper.toCompanyResponse(company))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<CompanyResponse>> updateCompany(CompanyRequest request) {
        String authorId = authHelper.getCurrentUserId();
        Company company = companyRepository.findByAuthor_id(authorId).get();
        company.setName(CharacterReference.encode(request.getName()));
        company.setAddress(CharacterReference.encode(request.getAddress()));
        company.setEmail(request.getEmail());
        company.setFields(request.getFields());
        company.setDescription(CharacterReference.encode(request.getDescription()));
        company.setBusinessType(request.getBusiness_type());
        company.setSize(request.getSize());
        company.setWebsite(request.getWebsite());
        company.setPhone(request.getPhone());
        company.setTaxCode(request.getTax_code());
        if(request.getLogo_url() != null && !request.getLogo_url().equals("")) {
            String logo_url = cloudinaryService.uploadBase64Image(request.getLogo_url(), "company_logo_" + authorId);
            company.setLogoUrl(logo_url);
        }
        if(request.getCover_photo() != null && !request.getCover_photo().equals("")) {
            String cover_photo_url = cloudinaryService.uploadBase64Image(request.getCover_photo(), "company_cover_photo_" + authorId);
            company.setCoverPhoto(cover_photo_url);
        }

        company = companyRepository.save(company);

        ApiResponse<CompanyResponse> response = ApiResponse.<CompanyResponse>builder()
                .message("Cập nhật thông tin công ty thành công!")
                .status(200)
                .result(companyMapper.toCompanyResponse(company))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<CompanyResponse>> getCompanyById(String companyId) {
        Optional<Company> companyOpt = companyRepository.findById(companyId);
        if(!companyOpt.isPresent()) {
            throw new NotFoundException("Không tìm thấy thông tin công ty!");
        }

        ApiResponse<CompanyResponse> response = ApiResponse.<CompanyResponse>builder()
                .message("Thông tin công ty")
                .status(200)
                .result(companyMapper.toCompanyResponse(companyOpt.get()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<CompanyResponse>> getCompanyByAuthor(String authorId) {
        Optional<Company> companyOpt = companyRepository.findByAuthor_id(authorId);
        if(!companyOpt.isPresent()) {
            throw new NotFoundException("Không tìm thấy thông tin công ty!");
        }

        ApiResponse<CompanyResponse> response = ApiResponse.<CompanyResponse>builder()
                .message("Thông tin công ty")
                .status(200)
                .result(companyMapper.toCompanyResponse(companyOpt.get()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<CompanyResponse>> getMyCompany() {
        String authorId = authHelper.getCurrentUserId();
        Optional<Company> companyOpt = companyRepository.findByAuthor_id(authorId);
        if(!companyOpt.isPresent()) {
            throw new NotFoundException("Không tìm thấy thông tin công ty!");
        }

        ApiResponse<CompanyResponse> response = ApiResponse.<CompanyResponse>builder()
                .message("Thông tin công ty")
                .status(200)
                .result(companyMapper.toCompanyResponse(companyOpt.get()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Map<String, Object>>> getListCompany(String search, Pageable pageable) {
        Page<Company> pageResult = null;
        if(search == null || search.isBlank()) {
            pageResult = companyRepository.findAll(pageable);
        } else {
            pageResult = companyRepository.findByNameContainingIgnoreCase(search, pageable);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("companies", pageResult.get().map(item -> companyMapper.toCompanyResponse(item)).collect(Collectors.toList()));
        result.put("currentPage", pageResult.getNumber());
        result.put("totalItems", pageResult.getTotalElements());
        result.put("totalPages", pageResult.getTotalPages());
        result.put("hasNext", pageResult.hasNext());
        result.put("hasPrevious", pageResult.hasPrevious());

        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
                .message("Danh sách công ty")
                .status(HttpStatus.OK.value())
                .result(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<String>>> getAuthorIdsByField(Short field) {
        List<String> ids = companyRepository.findAuthorIdsByField(field);

        ApiResponse<List<String>> response = ApiResponse.<List<String>>builder()
                .message("ID List")
                .status(HttpStatus.OK.value())
                .result(ids)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompaniesByAuthorIds(List<String> authorIds) {
        List<Company> companies = companyRepository.findByAuthorIdIn(authorIds);

        List<CompanyResponse> result = companies.stream().map(item -> companyMapper.toCompanyResponse(item)).collect(Collectors.toList());

        ApiResponse<List<CompanyResponse>> response = ApiResponse.<List<CompanyResponse>>builder()
                .status(200)
                .result(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
