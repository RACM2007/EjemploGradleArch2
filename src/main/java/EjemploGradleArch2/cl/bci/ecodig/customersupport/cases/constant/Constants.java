package cl.bci.ecodig.customersupport.cases.constant;

public class Constants {

    private Constants() {
    }

    //Represents inner id for first status
    public static final int ID_FIRST_STATUS = 1;

    //Represents name for unique identifier in service bus message
    public static final String SERVICE_BUS_MESSAGE_ID = "azure_service_bus_message_id";

    //Target Partner id value used in the case referral
    public static final String TARGET_PARTNER_ID = "Target Partner ID";

    //If Target Partner id value was not provied
    public static final String NO_TARGET_PARTNER_ID = "Partner ID not supplied";

    //Represents ISO 8601 UTC standard format
    public static final String ISO8601_FORMAT = "uuuu-MM-dd'T'HH:mm:ss.SSS'Z'";

    //Target Partner name value used in the case referral
    public static final String TARGET_PARTNER_NAME= "Target Partner Name";

    //Target Reason name value used in the case referral
    public static final String TARGET_REASON = "Reason";

    //Partners name value
    public static final String PARTNERS = "partners";

    //Products name value
    public static final String PRODUCTS = "products";

    //Reason name value
    public static final String PRODUCT_REASON = "reason";

    //Communication Channel name value
    public static final String COMMUNICATION_CHANNEL = "communicationChannel";

    //Type name value
    public static final String CASE_TYPE = "type";

    //Status name value
    public static final String CASE_STATUS = "status";

    //Literal value when the case is not found
    public static final String NOT_FOUND_CASE_MESSAGE = "Case not found";

    //Internal code when the case not have attachments
    public static final String NOT_ALLOW_ATTACHMENTS_CODE = "001";

    //Literal value when the case not have attachments
    public static final String NOT_ALLOW_ATTACHMENTS_MESSAGE = "Not allowed attachments for this case";

    //Represent the numeric value for filename position in the payload
    public static final int PAYLOAD_FILE_NAME_POSITION = 94;

    //Represent an empty string
    public static final String EMPTY_STRING = "";

    //Represents the parameter to search in the map to object the id partner
    public static final String ID_PARTNER = "id_partner";

    //Represents the default id partner
    public static final String ID_PARTNER_DEFAULT = "0";

    //Represents a text value where the attachments are ok
    public static final String OK_ATTACHMENTS = "OK";

    //Represents a text value where no attachments are provided
    public static final String WITHOUT_ATTACHMENTS = "Without Attachments";

    //Bearer name value
    public static final String BEARER_AUTH = "Bearer ";

    //Represents a String dot
    public static final String DOT = ".";

    //Represents a String number one literal
    public static final int NUMBER_ONE = 1;

    //Represents a a mb value in kb
    public static final int MEGABYTE_SIZE = 1024;

    //Represents an error message when the file is unnamed
    public static final String INVALID_FILE_NAME = "File name must be provided by link or name";

    //Represents the default name of the subscription key header
    public static final String OCP_APIM_SUBSCRIPTION_KEY = "Ocp-Apim-Subscription-Key";

    //Represents a message when a malware scan fails
    public static final String THREAT_FOUND_MALWARE = "Threat found: Malware Analysis";

    //Represents a message when credit card value is found in dlp analysis
    public static final String THREAT_FOUND_DLP = "Threat found: DLP Analysis";

    //Represents a String value for pdf extension
    public static final String PDF_EXTENSION = ".pdf";

    //Represents a value for the Dots Per Inch for images
    public static final float DPI_FOR_IMAGES = 300;

    //Represents a String value for png extension
    public static final String PNG_EXTENSION = "png";

}