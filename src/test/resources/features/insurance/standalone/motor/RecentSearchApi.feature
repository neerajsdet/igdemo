@motor
Feature: Verify Motor Insurance recent search api, endpoint:/protection/recent-search/api/rs/v1/recentsearch

  
  Scenario: Validate the motor insurance recent search api
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "motor_url" and endpoint "motor_recent_search_endpoint"
    Given set the request headers, query params
      | h:X-PIBPL-USER-SSO-TOKEN | h:X-PIBPL-USER-WALLET-TOKEN          | qp:vehicleType | qp:limit |
      | sso_token                | 1f4ef8d6-fddc-4a1c-af17-fc0e50659900 | two_wheeler    | 3        |
    Then perform the "GET" api call 5 times and validate response code 200
