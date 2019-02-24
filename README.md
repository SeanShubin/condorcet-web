# Condorcet Web

## Data structure
- user
    - name     (primary key name)
    - email    (unique email)
    - password (password)
- election
    - owner         (foreign key user name)
    - name          (primary key name)
    - start         (date?)
    - end           (date?)
    - secret ballot (boolean)
    - locked        (boolean)
- candidate
    - election name  (foreign key election)
    - candidate name (primary key name)
- voter
    - election name (foreign key election name)
    - user name     (foreign key user name)
- ballot
    - election name (foreign key election name)
    - user name     (foreign key user name)
- ranking
    - rank           (int)
    - candidate name (foreign key candidate name)
