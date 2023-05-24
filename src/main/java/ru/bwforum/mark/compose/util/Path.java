package ru.bwforum.mark.compose.util;

public enum Path {

    GET_COMPANY_BY_ID("company/{id}"),
    GET_CUSTOMER_BY_ID("customer/{id}"),
    GET_COMPANIES_BY_CUSTOMER_ID("company/customer/{id}"),
    GET_CUSTOMERS_BY_COMPANY_ID("customer/company/{id}"),
    BIND_CUSTOMER("company/bind"),
    UNBIND_CUSTOMER("company/unbind/{id}"),
    BIND_COMPANY("customer/bind"),
    UNBIND_COMPANY("customer/unbind/{id}");



    public final String path;

    Path(String path) {
        this.path = path;
    }
}
