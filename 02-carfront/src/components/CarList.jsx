import CarApi from "../apis/CarApi"
import { useEffect, useState } from "react"

function CarList() {
  const [cars, setCars] = useState([])

  const onLaunch = async () => {
    const response = await CarApi.get("/cars").catch((err) => console.log(err))
    console.log(response.data._embedded.cars)
    //setCars(response.data._embedded.cars)
  }

  useEffect(() => {
    onLaunch()
  }, [])

  return <div>HelloWorld</div>
}

export default CarList
