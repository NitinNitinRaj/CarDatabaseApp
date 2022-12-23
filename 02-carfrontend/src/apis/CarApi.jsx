import axios from "axios"

export default axios.create({
  baseURL: "http://localhost:8080/api/1.0",
})
