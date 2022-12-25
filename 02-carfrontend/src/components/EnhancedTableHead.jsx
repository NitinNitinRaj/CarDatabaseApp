import { TableCell, TableRow } from "@mui/material"
import TableHead from "@mui/material/TableHead"
import TableSortLabel from "@mui/material/TableSortLabel"
import { visuallyHidden } from "@mui/utils"
import Box from "@mui/material/Box"
const headCells = [
  {
    id: "id",
    disablePadding: true,
    label: "ID",
  },
  {
    id: "brand",
    disablePadding: false,
    label: "Brand",
  },
  {
    id: "model",
    disablePadding: false,
    label: "Model",
  },
  {
    id: "color",
    disablePadding: false,
    label: "Color",
  },
  {
    id: "year",
    disablePadding: false,
    label: "Year",
  },
  {
    id: "price",
    disablePadding: false,
    label: "Price",
  },
  {
    id: "registerNumber",
    disablePadding: false,
    label: "Rg No.",
  },
  {
    id: "edit",
    disablePadding: false,
    label: "",
  },
  {
    id: "delete",
    disablePadding: false,
    label: "",
  },
]

function EnhancedTableHead(props) {
  const { order, orderBy, onRequestSort } = props
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property)
  }

  return (
    <TableHead>
      <TableRow>
        {headCells.map((headCell) =>
          headCell.id !== ("delete" || "edit") ? (
            <TableCell
              key={headCell.id}
              align="right"
              padding={headCell.disablePadding ? "none" : "normal"}
              sortDirection={orderBy === headCell.id ? order : false}
            >
              <TableSortLabel
                active={orderBy === headCell.id}
                direction={orderBy === headCell.id ? order : "asc"}
                onClick={createSortHandler(headCell.id)}
              >
                {headCell.label}
                {orderBy === headCell.id ? (
                  <Box component="span" sx={visuallyHidden}>
                    {order === "desc"
                      ? "sorted descending"
                      : "sorted ascending"}
                  </Box>
                ) : null}
              </TableSortLabel>
            </TableCell>
          ) : (
            <TableCell
              key={headCell.id}
              align="right"
              padding={headCell.disablePadding ? "none" : "normal"}
            ></TableCell>
          )
        )}
      </TableRow>
    </TableHead>
  )
}

export default EnhancedTableHead
