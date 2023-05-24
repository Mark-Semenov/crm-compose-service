package ru.bwforum.mark.compose.dto;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Set<String> emails = new HashSet<>();
    private Set<String> phones = new HashSet<>();
    private String post;
    private String type;
    private String status;
    private Set<String> uniqueCompaniesId = new HashSet<>();
    private List<CompanyDTO> companyDTO;
    private boolean isDelete;

}
