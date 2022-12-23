import { useEffect } from "react"
import { useState } from "react"
import CarApi from "../apis/CarApi"

function CarList() {
  const [cars, setCars] = useState([])

  const afterLaunch = async () => {
    const response = await CarApi.get("/cars").catch((err) => console.log(err))
    console.log(response.data._embedded.cars)
    setCars(response.data._embedded.cars)
  }

  useEffect(() => {
    afterLaunch()
  }, [])

  return <div></div>
}

export default CarList
