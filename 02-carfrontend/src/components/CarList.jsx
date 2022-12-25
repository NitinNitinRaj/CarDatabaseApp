import {
  Box,
  Button,
  Grid,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TablePagination,
  TableRow,
} from "@mui/material"
import React, { useEffect, useState } from "react"
import CarApi from "../apis/CarApi"
import { getComparator, stableSort } from "../helper/SortingFunc"
import EnhancedTableHead from "./EnhancedTableHead"
import { ToastContainer, toast } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"
import AddCar from "./AddCar"
import EditCar from "./EditCar"
import { CSVLink } from "react-csv"

function CarList() {
  const [cars, setCars] = useState([])
  const [isEditOpen, setIsEditOpen] = useState(false)
  const [carEdit, setcarEdit] = useState({})
  const [order, setOrder] = useState("asc")
  const [orderBy, setOrderBy] = useState("brand")
  const [page, setPage] = React.useState(0)
  const [rowsPerPage, setRowsPerPage] = React.useState(4)

  const fetchCars = async () => {
    const token = sessionStorage.getItem("jwt")
    const response = await CarApi.get("/cars", {
      headers: { Authorization: token },
    }).catch((err) => {
      if (err.code === "ERR_NETWORK") {
        toast.error("Server Down", {
          position: "bottom-left",
          autoClose: 1500,
        })
      }
      console.log(err)
      err.response.data.message.map((msg) => {
        toast.error(msg, {
          position: "bottom-left",
          autoClose: 2500,
        })
      })
    })
    if (response.status === 200) {
      console.log(response.data._embedded.cars)
      setCars(response.data._embedded.cars)
      // toast.success("Table Reloaded", {
      //   position: "bottom-left",
      //   autoClose: 500,
      // })
    }
  }

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === "asc"
    setOrder(isAsc ? "desc" : "asc")
    setOrderBy(property)
  }

  const handleChangePage = (event, newPage) => {
    setPage(newPage)
  }

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10))
    setPage(0)
  }

  useEffect(() => {
    fetchCars()
  }, [])

  const onDelClick = async (value, id) => {
    if (window.confirm("Are you sure to delete?")) {
      const token = sessionStorage.getItem("jwt")
      const response = await CarApi.delete(value, {
        headers: { Authorization: token },
      }).catch((err) => {
        if (err.code === "ERR_NETWORK") {
          toast.error("Server Down", {
            position: "bottom-left",
            autoClose: 1500,
          })
        }
        console.log(err)
        err.response.data.message.map((msg) => {
          toast.error(msg, {
            position: "bottom-left",
            autoClose: 2500,
          })
        })
      })

      if (response.status === 204) {
        const updatedCarList = cars.filter((car) => car.id !== id)
        fetchCars()
        toast.warning("Car Deleted", {
          position: toast.POSITION.BOTTOM_LEFT,
        })
      }
    }
  }

  const addNewCar = (newCar) => {
    console.log(newCar)
    fetchCars()
  }

  const onEditClick = (car) => {
    setIsEditOpen(true)
    setcarEdit(car)
    console.log(car, isEditOpen)
  }

  const reactTable = (
    <Box>
      <TableContainer>
        <Table aria-labelledby="tableTitle">
          <EnhancedTableHead
            order={order}
            orderBy={orderBy}
            onRequestSort={handleRequestSort}
            rowCount={cars.length}
          />
          <TableBody>
            {stableSort(cars, getComparator(order, orderBy))
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((row) => {
                return (
                  <TableRow hover key={row.id}>
                    <TableCell
                      align="right"
                      padding="none"
                      component="th"
                      scope="row"
                    >
                      {row.id}
                    </TableCell>
                    <TableCell align="right">{row.brand}</TableCell>
                    <TableCell align="right">{row.model}</TableCell>
                    <TableCell align="right">{row.color}</TableCell>
                    <TableCell align="right">{row.year}</TableCell>
                    <TableCell align="right">{row.price}</TableCell>
                    <TableCell align="right" padding="normal">
                      {row.registerNumber}
                    </TableCell>
                    <TableCell align="right">
                      <Button onClick={() => onEditClick(row)} variant="text">
                        Edit
                      </Button>
                    </TableCell>
                    <TableCell>
                      <Button
                        onClick={() => onDelClick(row._links.self.href, row.id)}
                        variant="text"
                        color="warning"
                      >
                        Delete
                      </Button>
                    </TableCell>
                  </TableRow>
                )
              })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[4, 8, 16]}
        component="div"
        count={cars.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Box>
  )

  return (
    <div>
      <Grid container>
        <Grid item>
          <AddCar onChange={addNewCar} />
        </Grid>
        <Grid item style={{ padding: 15 }}>
          <CSVLink
            data={cars}
            filename={"Cars.csv"}
            style={{ textDecoration: "none" }}
          >
            <Button variant="text">Export CSV</Button>
          </CSVLink>
        </Grid>
      </Grid>

      {isEditOpen && (
        <EditCar
          onChange={addNewCar}
          savedCar={carEdit}
          setIsEditOpen={setIsEditOpen}
        />
      )}
      {reactTable}
      <ToastContainer autoClose={1500} />
    </div>
  )
}

export default CarList
