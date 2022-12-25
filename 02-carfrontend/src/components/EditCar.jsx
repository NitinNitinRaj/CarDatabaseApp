import Dialog from "@material-ui/core/Dialog"
import DialogActions from "@material-ui/core/DialogActions"
import DialogContent from "@material-ui/core/DialogContent"
import DialogTitle from "@material-ui/core/DialogTitle"
import { Button, FormControl, TextField } from "@mui/material"
import React, { useState } from "react"
import CarApi from "../apis/CarApi"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"
function EditCar({ onChange, savedCar, setIsEditOpen }) {
  const [open, setOpen] = useState(true)
  const [car, setCars] = useState(savedCar)

  const handleClose = () => {
    setOpen(false)
    setIsEditOpen(false)
  }

  const handleChange = (event) => {
    setCars({ ...car, [event.target.name]: event.target.value })
  }

  const addCar = async (car) => {
    const newCar = { ...car, year: Number(car.year), price: Number(car.price) }
    const token = sessionStorage.getItem("jwt")
    const response = await CarApi.put("/cars/" + newCar.id, newCar, {
      headers: {
        "Content-Type": "application/json",
        Authorization: token,
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
    if (response.status === 200) {
      onChange(response.data)
      toast.success("Succesffully edited the car.", {
        position: "bottom-left",
      })
    }
  }

  const onSave = () => {
    handleClose()
    addCar(car)
    setIsEditOpen(false)
  }

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Edit Car</DialogTitle>
        <DialogContent>
          <FormControl>
            <TextField
              fullWidth
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
            <TextField
              fullWidth
              required
              type="text"
              placeholder="Model"
              name="model"
              value={car.model}
              onChange={handleChange}
            />
            <br />
            <TextField
              fullWidth
              required
              type="text"
              placeholder="Color"
              name="color"
              value={car.color}
              onChange={handleChange}
            />
            <br />
            <TextField
              fullWidth
              required
              type="text"
              placeholder="Register Number"
              name="registerNumber"
              value={car.registerNumber}
              onChange={handleChange}
            />
            <br />
            <TextField
              fullWidth
              required
              type="text"
              name="year"
              placeholder="Car model year"
              value={car.year}
              onChange={handleChange}
            />
            <br />
            <TextField
              fullWidth
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
            color="secondary"
            size="small"
            onClick={handleClose}
          >
            Cancel
          </Button>
          <Button variant="text" color="primary" size="small" onClick={onSave}>
            Save
          </Button>
        </DialogActions>
      </Dialog>
      <ToastContainer autoClose={1500} />
    </div>
  )
}

export default EditCar
