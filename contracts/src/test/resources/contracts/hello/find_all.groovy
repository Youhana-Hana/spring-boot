import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Find all persons"
    request {
        url "/persons"
        method GET()
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body ([
                [id: '1', name: 'R', sirName: 'F'],
                [id: '2', name: 'J', sirName: 'G']
        ])
    }
}