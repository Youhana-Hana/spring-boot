import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "Should return person by id 1"
    request {
        url "/persons/1"
        method GET()
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                id: "1",
                name: "Richard",
                sirName: "Fin"
        )
    }
}
