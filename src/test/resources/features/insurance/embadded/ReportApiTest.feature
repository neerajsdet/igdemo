@shop-insurance
Feature: Verify Insurance Embedded Report api, endpoint:v1/public/embedded/report


  Scenario Outline: Validate the api for scenario - <SCENARIO>
    Given set the request base url "insurance_url" and endpoint "embedded_report_api_endpoint"
    Given generate random alphanumeric string with length 15 and save as key "merchantId"
    Given set the request headers, query params and payload with json file name "embedded_report.json"
      | h:sso_token                          | h:Content-Type   | merchantId    | reason   |
      | 337fb0f4-16e2-4e13-9a92-b01ac6272800 | application/json | <MERCHANT_ID> | <REASON> |
      Then perform the "POST" api call, verify response code 200 and below response data
        | code                | merchant_id   |
        | <EXP_RESPONSE_CODE> | <MERCHANT_ID> |

    Examples:
      | SCENARIO                          | EXP_RESPONSE_CODE | MERCHANT_ID | REASON                  |
      | Reason is NOT_ELIGIBLE            | 200               | merchantId  | NOT_ELIGIBLE            |
      | Reason is HIGHER_INSURANCE_AMOUNT | 200               | merchantId  | HIGHER_INSURANCE_AMOUNT |
      | Reason is blank                   | 200               | merchantId  |                         |
      | Reason is null                    | 200               | merchantId  | null                    |
      | Reason is dummy value             | 200               | merchantId  | xxxxxx                  |



  Scenario Outline: Validate the api for scenario - <SCENARIO>
    Given set the request base url "insurance_url" and endpoint "embedded_report_api_endpoint"
    Given generate random alphanumeric string with length 15 and save as key "merchantId"
    Given set the request headers, query params and payload with json file name "embedded_report.json"
      | h:sso_token | h:Content-Type   | merchantId    |
      | <SSO_TOKEN> | application/json | <MERCHANT_ID> |
    Then perform the "POST" api call, verify response code <EXP_RESPONSE_CODE> and below response data
      | code                | error_message   |
      | <EXP_RESPONSE_CODE> | <ERROR_MESSAGE> |

    Examples:
      | SCENARIO             | EXP_RESPONSE_CODE | ERROR_MESSAGE                             | MERCHANT_ID | SSO_TOKEN                            |
      | Invalid sso_token    | 412               | Invalid Sso Token/Unable to Fetch Cust Id | merchantId  | xxx-yyyy-zzzz                        |
      | Merchant id is null  | 412               | Mandatory Params are missing              | null        | 337fb0f4-16e2-4e13-9a92-b01ac6272800 |
      | Merchant id is blank | 412               | Mandatory Params are missing              |             | 337fb0f4-16e2-4e13-9a92-b01ac6272800 |




