http GET 'http://localhost:8080/data/request/45afdc62-41da-4587-9f2c-5c0420fbc692?t=12h'


http GET 'http://localhost:8080/data/project/eb4642c4-3146-45b9-961f-b7b66ef3bb0f?t=12h'


http GET 'http://localhost:8080/data/all?t=12h'


http GET 'http://localhost:8080/project/'


http GET 'http://localhost:8080/project/eb4642c4-3146-45b9-961f-b7b66ef3bb0f'


http GET 'http://localhost:8080/project/all'


http --json POST 'http://localhost:8080/project/new' \
    'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3Mjg3MTc1NDEsImV4cCI6MTczMTM0NzI4Nywic3ViIjoidGVzdF91c2VybmFtZSIsImlzcyI6InN0YXR1cy5tYXJpb3NtLnh5eiJ9.crrm-Cn1mJZFmUWzBw1dqMdfvmBUZJYapej261vw55upBXLQnuM_brW56XOSMaQqjG0Ip9GcpPjYMzGwIeoDsQ' \
    'Content-Type':'application/json; charset=utf-8' \
    name="test_project"


http DELETE 'http://localhost:8080/project/cbfc4d92-85cd-4cc6-8f38-2cb138724b45' \
    'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3Mjg3MTc1NDEsImV4cCI6MTczMTM0NzI4Nywic3ViIjoidGVzdF91c2VybmFtZSIsImlzcyI6InN0YXR1cy5tYXJpb3NtLnh5eiJ9.crrm-Cn1mJZFmUWzBw1dqMdfvmBUZJYapej261vw55upBXLQnuM_brW56XOSMaQqjG0Ip9GcpPjYMzGwIeoDsQ'


http --json GET 'http://localhost:8080/request/' \
    'Content-Type':'application/json; charset=utf-8' \
    code="200" \
    interval="60" \
    project="eb4642c4-3146-45b9-961f-b7b66ef3bb0f" \
    method="GET" \
    name="Test" \
    uri="https://google.com"


http --json GET 'http://localhost:8080/request/45afdc62-41da-4587-9f2c-5c0420fbc692' \
    'Content-Type':'application/json; charset=utf-8' \
    code="200" \
    interval="60" \
    project="eb4642c4-3146-45b9-961f-b7b66ef3bb0f" \
    method="GET" \
    name="Test" \
    uri="https://google.com"


http --json GET 'http://localhost:8080/request/project/eb4642c4-3146-45b9-961f-b7b66ef3bb0f' \
    'Content-Type':'application/json; charset=utf-8' \
    code="200" \
    interval="60" \
    project="eb4642c4-3146-45b9-961f-b7b66ef3bb0f" \
    method="GET" \
    name="Test" \
    uri="https://google.com"


http --json GET 'http://localhost:8080/request/all' \
    'Content-Type':'application/json; charset=utf-8' \
    code="200" \
    interval="60" \
    project="eb4642c4-3146-45b9-961f-b7b66ef3bb0f" \
    method="GET" \
    name="Test" \
    uri="https://google.com"


http --json POST 'http://localhost:8080/request/new' \
    'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3Mjg3MTc1NDEsImV4cCI6MTczMTM0NzI4Nywic3ViIjoidGVzdF91c2VybmFtZSIsImlzcyI6InN0YXR1cy5tYXJpb3NtLnh5eiJ9.crrm-Cn1mJZFmUWzBw1dqMdfvmBUZJYapej261vw55upBXLQnuM_brW56XOSMaQqjG0Ip9GcpPjYMzGwIeoDsQ' \
    'Content-Type':'application/json; charset=utf-8' \
    code="200" \
    interval="60" \
    project="eb4642c4-3146-45b9-961f-b7b66ef3bb0f" \
    method="GET" \
    name="Test" \
    uri="https://google.com"


http --json PUT 'http://localhost:8080/request/45afdc62-41da-4587-9f2c-5c0420fbc692' \
    'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3Mjg3MTc1NDEsImV4cCI6MTczMTM0NzI4Nywic3ViIjoidGVzdF91c2VybmFtZSIsImlzcyI6InN0YXR1cy5tYXJpb3NtLnh5eiJ9.crrm-Cn1mJZFmUWzBw1dqMdfvmBUZJYapej261vw55upBXLQnuM_brW56XOSMaQqjG0Ip9GcpPjYMzGwIeoDsQ' \
    'Content-Type':'application/json; charset=utf-8' \
    code="200" \
    interval="60" \
    project="eb4642c4-3146-45b9-961f-b7b66ef3bb0f" \
    method="GET" \
    name="Test" \
    uri="https://google.com"


http --json DELETE 'http://localhost:8080/request/3d6d45ae-3c36-44da-87b2-28fb1f6ccef3' \
    'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3Mjg3MTc1NDEsImV4cCI6MTczMTM0NzI4Nywic3ViIjoidGVzdF91c2VybmFtZSIsImlzcyI6InN0YXR1cy5tYXJpb3NtLnh5eiJ9.crrm-Cn1mJZFmUWzBw1dqMdfvmBUZJYapej261vw55upBXLQnuM_brW56XOSMaQqjG0Ip9GcpPjYMzGwIeoDsQ' \
    'Content-Type':'application/json; charset=utf-8' \
    code="200" \
    interval="60" \
    project="eb4642c4-3146-45b9-961f-b7b66ef3bb0f" \
    method="GET" \
    name="Test" \
    uri="https://google.com"


http --form POST 'http://localhost:8080/auth/register' \
    'Content-Type':'text/plain; charset=utf-8' \
    'data'=$'{
  \"username\": \"test_username\",
  \"password\": \"test_password\"
}'


http --json POST 'http://localhost:8080/auth/login' \
    'Content-Type':'application/json; charset=utf-8' \
    username="test_username" \
    password="test_password"


