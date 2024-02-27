@motor
Feature: Verify Motor Insurance Order api, endpoint:/protection/motor-proposal/api/v1/policies


  Scenario: Validate the motor policy api
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "motor_url" and endpoint "motor_order_endpoint"
    Given set the request headers, query params
      | h:X-PIBPL-USER-SSO-TOKEN | h:X-PIBPL-USER-WALLET-TOKEN          | qp: AttemptId|
      | sso_token                | 1f4ef8d6-fddc-4a1c-af17-fc0e50659900 | 123456       |
    Then perform the "GET" api call 1 times and validate response code 200


  Scenario: Validate the motor policy api with invalid wallet token
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "motor_url" and endpoint "motor_rto_endpoint"
    Given set the request headers, query params
      | h:X-PIBPL-USER-SSO-TOKEN | h:X-PIBPL-USER-WALLET-TOKEN          |
      | sso_token                | 1f4ef8d6-fc0e50659900 |
    Then perform the "GET" api call 1 times and validate response code 400