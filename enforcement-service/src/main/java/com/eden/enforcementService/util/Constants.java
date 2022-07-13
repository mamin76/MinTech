package com.eden.enforcementService.util;

public class Constants {
    public static final String TOKEN_TYPE = "Bearer ";

    public static final class ProducerTopics {
        public static final String EMAIL_TOPIC = "email_topic";
        public static final String CITATION_KPI_TOPIC = "citation_kpi_topic";
        public static final String SCAN_KPI_TOPIC = "scan_kpi_topic";
        public static final String VEHICLE_CREATED_TOPIC = "create_vehicle_topic";
    }

    public static final class ErrorKeys {
        public static final String PENALTY_NAME_REQUIRED = "PENALTY_NAME_REQUIRED";
        public static final String PENALTY_VALUE_REQUIRED = "PENALTY_VALUE_REQUIRED";
        public static final String PENALTY_TO_EMAIL_NOT_VALID = "PENALTY_TO_EMAIL_NOT_VALID";
        public static final String PENALTY_TO_EMAIL_REQUIRED = "PENALTY_TO_EMAIL_REQUIRED";
        public static final String PENALTY_TO_EMAIL_SUBJECT_REQUIRED = "PENALTY_TO_EMAIL_SUBJECT_REQUIRED";
        public static final String VIOLATION_NAME_REQUIRED = "VIOLATION_NAME_REQUIRED";
        public static final String PENALTY_TO_EMAIL_HEADER_REQUIRED = "PENALTY_TO_EMAIL_HEADER_REQUIRED";
        public static final String PENALTY_TYPE_REQUIRED = "PENALTY_TYPE_REQUIRED";
        public static final String PENALTY_MONETARY_REQUIRED = "PENALTY_MONETARY_REQUIRED";
        public static final String EMPTY_START_DATE = "EMPTY_START_DATE";
        public static final String EMPTY_END_DATE = "EMPTY_END_DATE";
        public static final String PENALTY_ID_REQUIRED = "PENALTY_ID_REQUIRED";
        public static final String VIOLATION_ID_REQUIRED = "VIOLATION_ID_REQUIRED";
        public static final String OPERATION_ID_REQUIRED = "OPERATION_ID_REQUIRED";
        public static final String PENALTY_NOT_FOUND = "PENALTY_NOT_FOUND";
        public static final String VIOLATION_NOT_FOUND = "VIOLATION_NOT_FOUND";
        public static final String DUPLICATE_IN_DATA = "DUPLICATE_IN_DATA";
        public static final String VIOLATION_OPERATION_EXIST = "VIOLATION_OPERATION_EXIST";
        public static final String PENALTY_METHOD_REQUIRED = "PENALTY_METHOD_REQUIRED";
        public static final String VIOLATION_NOT_FOUND_IN_OPERATION = "VIOLATION_NOT_FOUND_IN_OPERATION";
        public static final String VIOLATION_PENALTY_NOT_FOUND_IN_OPERATION = "VIOLATION_PENALTY_NOT_FOUND_IN_OPERATION";
        public static final String EMPTY_PLATE_NUMBER_AR = "EMPTY_PLATE_NUMBER_AR";
        public static final String EMPTY_PLATE_NUMBER_EN = "EMPTY_PLATE_NUMBER_EN";
        public static final String EMPTY_USER_NAME = "EMPTY_USER_NAME";
        //        public static final String PENALTY_MONETARY_REQUIRED = "PENALTY_MONETARY_REQUIRED";
//        public static final String EMPTY_START_DATE = "EMPTY_START_DATE";
//        public static final String EMPTY_END_DATE = "EMPTY_END_DATE";
//        public static final String EMPTY_PLATE_NUMBER_AR = "EMPTY_PLATE_NUMBER_AR";
//        public static final String EMPTY_PLATE_NUMBER_EN = "EMPTY_PLATE_NUMBER_EN";
        public static final String EMPTY_CITATION_COUNTRY_ID = "EMPTY_CITATION_COUNTRY_ID";
        public static final String EMPTY_SHIFT_WORK_FORCE_ID = "EMPTY_SHIFT_WORK_FORCE_ID";
        public static final String EMPTY_CITATION_STATUS = "EMPTY_CITATION_STATUS";
        public static final String EMPTY_CITATION_LONGITUDE = "EMPTY_CITATION_LONGITUDE";
        public static final String EMPTY_CITATION_LATITUDE = "EMPTY_CITATION_LATITUDE";
        public static final String EMPTY_CITATIONS_REQUEST = "EMPTY_CITATIONS_REQUEST";
        public static final String EMPTY_OPERATION_ID = "EMPTY_OPERATION_ID";
        public static final String EMPTY_VIOLATION_ID = "EMPTY_VIOLATION_ID";
        public static final String EMPTY_CITATIONS_EVIDENCE_REQUEST = "EMPTY_CITATIONS_EVIDENCE_REQUEST";
        public static final String CITATION_TYPE_REQUIRED = "CITATION_TYPE_REQUIRED";
        public static final String ACTION_COUNT_REQUIRED = "ACTION_COUNT_REQUIRED";
        public static final String EMPTY_CITATION_ID = "EMPTY_CITATION_ID";
        public static final String EMPTY_CITATION_VOID_REASON = "EMPTY_CITATION_VOID_REASON";
        public static final String CITATION_NOT_FOUND = "CITATION_NOT_FOUND";
        public static final String EMPTY_PENALTIES = "EMPTY_PENALTIES";
        public static final String OPERATION_VIOLATION_ID_REQUIRED = "OPERATION_VIOLATION_ID_REQUIRED";
        public static final String PENALTY_FEES_REQUIRED = "PENALTY_FEES_REQUIRED1";
        public static final String PENALTY_ACTION_REQUIRED = "PENALTY_ACTION_REQUIRED";
        public static final String EMPTY_CITATION_PENALTY_ID = "EMPTY_CITATION_PENALTY_ID";
        public static final String CITATION_PENALTY_NOT_FOUND = "CITATION_PENALTY_NOT_FOUND";
        public static final String EMPTY_CITATION_PENALTY = "EMPTY_CITATION_PENALTY";
        public static final String INVALID_ACTION_COUNT = "INVALID_ACTION_COUNT";
        public static final String EMPTY_ITEM_NAME = "EMPTY_ITEM_NAME";
        public static final String EMPTY_AMOUNT = "EMPTY_AMOUNT";
        public static final String POSITIVE_ITEM_AMOUNT = "POSITIVE_ITEM_AMOUNT";
        public static final String EMPTY_ITEMS = "EMPTY_ITEMS";
        public static final String EMPTY_PAYMENT_METHOD = "EMPTY_PAYMENT_METHOD";
        public static final String EMPTY_SOURCE = "EMPTY_SOURCE";
        public static final String EMPTY_INVOICES = "EMPTY_INVOICES";
        public static final String ERROR_UNAUTHORIZED = "ERROR_UNAUTHORIZED";
        public static final String EMPTY_CITATION_ADDRESS = "EMPTY_CITATION_ADDRESS";
        public static final String EMPTY_COUNTRY_ID = "EMPTY_COUNTRY_ID";
        public static final String EMPTY_MAKE_ID = "EMPTY_MAKE_ID";
        public static final String EMPTY_COLOR_ID = "EMPTY_COLOR_ID";
        public static final String EMPTY_MODEL = "EMPTY_MODEL";
        public static final String EMPTY_VEHICLE_SOURCE = "EMPTY_VEHICLE_SOURCE";
        public static final String EMPTY_VEHICLE_TYPE = "EMPTY_VEHICLE_TYPE";
        public static final String BLACK_LIST_VEHICLE_NOT_FOUND = "BLACK_LIST_VEHICLE_NOT_FOUND";
        public static final String BLACK_LIST_ID_REQUIRED = "BLACK_LIST_ID_REQUIRED";
        public static final String SOME_PENALTIES_NOT_FOUND = "SOME_PENALTIES_NOT_FOUND";
        public static final String EMPTY_COUNTRY_NAME = "EMPTY_COUNTRY_NAME";
        public static final String EMPTY_FROM_DATE = "EMPTY_FROM_DATE";
        public static final String EMPTY_TO_DATE = "EMPTY_TO_DATE";
        public static final String EMPTY_FROM_TIME = "EMPTY_FROM_TIME";
        public static final String EMPTY_TO_TIME = "EMPTY_TO_TIME";
        public static final String EMPTY_COUNTRY = "EMPTY_COUNTRY";
        public static final String EMPTY_WHITELIST_DESC = "EMPTY_WHITELIST_DESC";
        public static final String EMPTY_REASON_EN = "EMPTY_REASON_EN";
        public static final String EMPTY_REASON_AR = "EMPTY_REASON_AR";
        public static final String EMPTY_REASON_TYPE = "EMPTY_REASON_TYPE";
        public static final String EMPTY_REQUEST = "EMPTY_REQUEST";
        public static final String EN_DESCRIPTION_REQUIRED="EN_DESCRIPTION_REQUIRED";
        public static final String AR_DESCRIPTION_REQUIRED="AR_DESCRIPTION_REQUIRED";
        public static final String VIOLATION_EXIST="VIOLATION_EXIST";
        public static final String PENALTY_EXIST="PENALTY_EXIST";
        public static final String EMPTY_CITATION_VOID_REASON_ID="EMPTY_CITATION_VOID_REASON_ID";
        public static final String EMPTY_PLATE_NUMBER = "EMPTY_PLATE_NUMBER";
        public static final String THERE_ARE_DUPLICATED_CITATIONS="THERE_ARE_DUPLICATED_CITATIONS";
        public static final String NOT_VALID_VIOLATION_IDS="NOT_VALID_VIOLATION_IDS";
        public static final String NOT_VALID_EVIDENCE_IDS = "NOT_VALID_EVIDENCE_IDS";
        public static final String CITATION_ALREADY_SETTLED = "CITATION_ALREADY_SETTLED";
        public static final String OPERATION_NOT_EXISTS = "OPERATION_NOT_EXISTS";

    }

    public static final class SuccessMessages {
        public static final String PENALTIES_ADDED = "Successfully Saved All Penalties";
        public static final String CITATION_ADDED = "Successfully Saved All Citation";
        public static final String VIOLATION_ADDED = "Successfully Saved the Violation";
        public static final String SUCCESSFULLY_ADDED = "Successfully Saved";
        public static final String CITATION_VOIDED = "CITATION_VOIDED";
        public static final String VIOLATION_DELETED = "VIOLATION_DELETED";
        public static final String VIOLATION_UPDATED = "VIOLATION_UPDATED";
        public static final String PENALTIES_DELETED = "PENALTIES_DELETED";

        public static final String BLACK_LIST_VEHICLE = "Successfully Saved Black List Vehicle";
        public static final String BLACK_LIST_VEHICLE_DELETED = "Successfully Deleted Black List Vehicle";

        public static final String BLACK_LIST_VEHICLE_EXISTS = "Black List Vehicle already Exists";
        public static final String BLACK_LIST_VEHICLE_WAS_DELETED_AND_ADDED_AGAIN = "BLACK_LIST_VEHICLE_WAS_DELETED_AND_ADDED_AGAIN";
    }

    public static final class Sorting {
        public static final String CREATION_DATE = "createdDate";
    }
    public static final String arName = "عقوبة القائمة سوداء";
    public static final String enName = "BlackListPenalty";


}
