@motor
Feature: Verify Insurance Policy api, endpoint:/protection/recent-search/api/rs/v1/recentsearch

  
  Scenario: Validate the policy api
    Given set the request base url "motor_url" and endpoint "motor_policy_api_endpoint"
    Given set the request headers and query params
      | h:X-PIBPL-USER-SSO-TOKEN             | h:X-PIBPL-USER-WALLET-TOKEN          | qp:vehicleType | qp:limit |
      | 1f4ef8d6-fddc-4a1c-af17-fc0e50659900 | 1f4ef8d6-fddc-4a1c-af17-fc0e50659900 | two_wheeler    | 3        |
    Then perform the "GET" api call and validate response code 200
    Then validate the api response for below key
      | [0].id | [0].model.name | [0].rto.city.popularityRank | [0].rto.city.name |
      | 16605  | Meteor         | null                        |                   |
