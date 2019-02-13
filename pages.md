# Pages

## Login
- header: title
- span: error
- input: name or email
- password: password
- button: login
- link: register

## Register
- header: title
- span: error
- input: name
- input: email
- input: password
- input: confirm password
- button: register
- link: login

## Home
- header: title
- input-and-button: create election
- button: my votes
- table: elections
    - text: owner
    - text: name
    - text: start
    - text: end
    - text: secret ballot
    - text: allow monitoring
    - button: edit 
- link: logout

## Election
- header: title
- input: name
- date-or-manual: start
- date-or-manual: end
- checkbox: secret ballot
- checkbox: allow monitoring 
- textarea: candidates
- table: voters
- button: apply changes
- link: home
- link: logout

## Ballots
- header: title
- table: active elections
    - button: ballot
    - text: name
- table: my previous votes
    - button: ballot
    - text: name
- link: home
- link: logout

## Ballot
- header: title
- table: rankings
    - number: rank
    - text: candidate name 
- button: cast ballot
- button: revoke ballot 
- link: home
- link: logout
