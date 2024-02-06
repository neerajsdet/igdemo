@motor
Feature: Verify Insurance Policy api, endpoint:/protection/recent-search/api/rs/v1/recentsearch

  
  Scenario: Validate the policy api
    Given generate the sso token with mobile "9899946472", password "paytm@123" and save in global data with key "sso_token"
    Given set the request base url "motor_url" and endpoint "motor_policy_api_endpoint"
    Given set the request headers and query params
      | h:X-PIBPL-USER-SSO-TOKEN | h:X-PIBPL-USER-WALLET-TOKEN          | qp:vehicleType | qp:limit |
      | sso_token                | 1f4ef8d6-fddc-4a1c-af17-fc0e50659900 | two_wheeler    | 3        |
    Then perform the "GET" api call and validate response code 200
#    Then perform the "GET" api call, verify response code 200 and below response data
#      | [0].id | [0].model.name | [0].rto.city.popularityRank | [0].rto.city.name |
#      | 16820  | Platina 110    | null                        | Mumbai            |
