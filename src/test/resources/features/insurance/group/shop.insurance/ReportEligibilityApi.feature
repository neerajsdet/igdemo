@shop-ins
Feature: Verify Report Eligibility API, endpoint: /lending-distribution-bff/insurances/side-effect/report-eligibility

  Background:
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"


  Scenario Outline: Validate report eligibility api for scenario: <scenario>
    Given set the request base url "insurance_bff_url" and endpoint "shop_ins_report_eligibility_endpoint"
    Given set the request headers, query params and payload with json file name "report_eligibility.json"
      | h:sso_token | h:Content-Type   | qp:product     | qp:journey | reason   |
      | sso_token   | application/json | shop-insurance | group      | <reason> |
    Then perform the "POST" api call 5 times, verify response code 201 and below response data
      | code |
      | 200  |

    Examples:
      | scenario                | reason                  |
      | Higher Insurance Amount | HIGHER_INSURANCE_AMOUNT |
      | Not Eligible            | NOT_ELIGIBLE            |



  Scenario Outline: Validate report eligibility api for negative scenario: <scenario>
    Given set the request base url "insurance_bff_url" and endpoint "shop_ins_report_eligibility_endpoint"
    Given set the request headers, query params and payload with json file name "report_eligibility.json"
      | h:sso_token | h:Content-Type   | qp:product     | qp:journey | reason   |
      | sso_token   | application/json | shop-insurance | group      | <reason> |
    Then perform the "POST" api call 5 times, verify response code 400 and below response data
      | error     |
      | <message> |

    Examples:
      | scenario        | reason | message     |
      | reason is null  | null   | Bad Request |
      | reason is blank |        | Bad Request |