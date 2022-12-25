import { Button, TextField } from "@material-ui/core"
import { FormControl, Container } from "@mui/material"
import { useState } from "react"
import CarApi from "../apis/CarApi"
import { ToastContainer, toast } from "react-toastify"
import axios from "axios"
import CarList from "./CarList"

function Login() {
  const [user, setUser] = useState({ username: "", password: "" })
  const [isAuthenticated, setAuth] = useState(false)

  const handleChange = (event) => {
    setUser({ ...user, [event.target.name]: event.target.value })
  }
  const handleSubmit = () => {
    console.log(user)
    signUp()
    setUser({ username: "", password: "" })
  }

  const signUp = async () => {
    const response = await CarApi.post("/users/register", user).catch((err) => {
      err.response.data.message.map((msg) => {
        toast.error(msg, {
          position: "bottom-left",
        })
      })
    })
    console.log(response)
    if (response.status === 201) {
      toast.success("Your account has been created.", {
        position: "bottom-left",
      })
    }
  }
  const handleLogIn = async () => {
    const response = await axios
      .post("http://localhost:8080/authenticate", user)
      .catch((err) => {
        console.log(err)
        toast.error(err.response.data, {
          position: "bottom-left",
        })
      })
    console.log(response)
    if (response.status === 200) {
      const jwtToken = response.headers.get("Authorization")
      console.log(jwtToken)
      if (jwtToken !== null) {
        sessionStorage.setItem("jwt", jwtToken)
        setAuth(true)
      }
    }
  }
  if (isAuthenticated === true) {
    return <CarList />
  } else
    return (
      <Container maxWidth="xs">
        <FormControl fullWidth>
          <TextField
            fullWidth
            style={{ marginBottom: 10, marginTop: 100 }}
            type="text"
            margin="dense"
            placeholder="Username"
            name="username"
            value={user.username}
            onChange={handleChange}
          />
          <TextField
            fullWidth
            style={{ marginBottom: 15 }}
            type="password"
            placeholder="Password"
            name="password"
            value={user.password}
            onChange={handleChange}
          />
          <Button
            style={{ marginBottom: 15 }}
            variant="outlined"
            color="primary"
            onClick={handleLogIn}
          >
            Login
          </Button>
          <Button variant="outlined" color="primary" onClick={handleSubmit}>
            Sign Up
          </Button>
        </FormControl>
        <ToastContainer autoClose={1500} />
      </Container>
    )
}

export default Login
