import { Input } from "@material-ui/core"
import Dialog from "@material-ui/core/Dialog"
import DialogActions from "@material-ui/core/DialogActions"
import DialogContent from "@material-ui/core/DialogContent"
import DialogTitle from "@material-ui/core/DialogTitle"
import { Button, FormControl } from "@mui/material"
import React, { useState } from "react"
import CarApi from "../apis/CarApi"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"
function AddCar({ onChange }) {
  const [open, setOpen] = useState(false)
  const [car, setCars] = useState({
    brand: "",
    model: "",
    color: "",
    registerNumber: "",
    year: "",
    price: "",
  })

  const handleClose = () => {
    setOpen(false)
  }

  const handleOpen = () => {
    setOpen(true)
  }

  const handleChange = (event) => {
    setCars({ ...car, [event.target.name]: event.target.value })
  }

  const addCar = async (car) => {
    const newCar = { ...car, year: Number(car.year), price: Number(car.price) }
    const response = await CarApi.post("/cars", newCar, {
      headers: {
        "Content-Type": "application/json",
      },
    }).catch((err) => {
      console.log(err)
      if (err.code === "ERR_NETWORK") {
        toast.error("Server Down", {
          position: "bottom-left",
          autoClose: 1500,
        })
      }
      err.response.data.message.map((msg) => {
        toast.error(msg, {
          position: "bottom-left",
          autoClose: 2500,
        })
      })
    })
    if (response.status === 201) {
      onChange(response.data)
      toast.success("Succesffully added a new car.", {
        position: "bottom-left",
      })
    }
  }

  const onSave = () => {
    handleClose()
    addCar(car)
    setCars({
      ...car,
      brand: "",
      model: "",
      color: "",
      registerNumber: "",
      year: "",
      price: "",
    })
  }

  return (
    <div>
      <Button
        style={{ margin: 10 }}
        variant="contained"
        color="primary"
        onClick={handleOpen}
      >
        New Car
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>New Car</DialogTitle>
        <DialogContent>
          <FormControl>
            <Input
              required
              autoFocus
              margin="dense"
              type="text"
              placeholder="Brand"
              name="brand"
              value={car.brand}
              onChange={handleChange}
            />
            <br />
            <Input
              required
              type="text"
              placeholder="Model"
              name="model"
              value={car.model}
              onChange={handleChange}
            />
            <br />
            <Input
              required
              type="text"
              placeholder="Color"
              name="color"
              value={car.color}
              onChange={handleChange}
            />
            <br />
            <Input
              required
              type="text"
              placeholder="Register Number"
              name="registerNumber"
              value={car.registerNumber}
              onChange={handleChange}
            />
            <br />
            <Input
              required
              type="text"
              name="year"
              placeholder="Car model year"
              value={car.year}
              onChange={handleChange}
            />
            <br />
            <Input
              required
              type="text"
              name="price"
              placeholder="Price"
              value={car.price}
              onChange={handleChange}
            />
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button
            variant="text"
            color="warning"
            size="small"
            onClick={handleClose}
          >
            Cancel
          </Button>
          <Button variant="text" color="success" size="small" onClick={onSave}>
            Save
          </Button>
        </DialogActions>
      </Dialog>
      <ToastContainer autoClose={1500} />
    </div>
  )
}

export default AddCar
