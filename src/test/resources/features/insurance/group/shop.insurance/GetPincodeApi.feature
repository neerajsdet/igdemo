@shop-ins
Feature: Verify Get Pincode API, endpoint: /lending-distribution-bff/insurances/side-effect/address/pincode/124001

  Background:
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"


  Scenario: Validate get pincode api for scenario: <scenario>
    Given set the request base url "insurance_bff_url" and endpoint "shop_ins_get_pincode_endpoint"
    Given set the request headers, query params
      | h:sso_token | h:Content-Type   | qp:product     | qp:journey |
      | sso_token   | application/json | shop-insurance | group      |
    Then perform the "GET" api call 5 times, verify response code 200 and below response data
      | pincode | city   |
      | 124001  | Rohtak |

