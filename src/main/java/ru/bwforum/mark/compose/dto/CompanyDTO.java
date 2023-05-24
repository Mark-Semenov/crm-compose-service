package ru.bwforum.mark.compose.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private String id;
    private String title;
    private String type;
    private final Set<String> emails = new HashSet<>();
    private final Set<String> phones = new HashSet<>();
    private final Set<String> websites = new HashSet<>();
    private final Set<String> uniqueCustomersId = new HashSet<>();
    private Requisites requisites;
    private List<CustomerDTO> customerDTO;
    private boolean isDelete;

}
