import { AppBar, Toolbar, Typography } from "@mui/material"
import CarList from "./components/CarList"

function App() {
  return (
    <div className="App">
      <AppBar position="static" color="default">
        <Toolbar>
          <Typography variant="h6" color="inherit">
            CarList
          </Typography>
        </Toolbar>
      </AppBar>
      <CarList />
    </div>
  )
}
export default App
