import { FormControl, Grid, InputLabel, MenuItem, Pagination, Paper, Select } from "@mui/material";
import React, { useEffect, useState } from "react";
import { RouteComponentProps } from "react-router";
import PaginatedResponse from "../common/models/PaginatedResponse";
import IPage from "../common/routes/page";
import Navigation from "../components/navigation";
import InventoryList from "./components/InventoryList";
import Inventory from "./inventoryModel";
import InventoryService from "./inventoryService";
import InventoryStatus from "./inventoryStatus";

const InventoryPage: React.FunctionComponent<IPage & RouteComponentProps<any>> = props => {

    const [libraryId, setLibraryId] = useState<number>();
    const [inventory, setInventory] = useState<PaginatedResponse<Inventory>>();
    const [page, setPage] = useState<number>(0);
    const [pageSize, setPageSize] = useState<number>(3);
    const [status, setStatus] = useState<InventoryStatus>(InventoryStatus.ALL);

    useEffect( () => {
        let libId = props.match.params.libraryId;
        setLibraryId(libId);
        InventoryService.getInventoryByLibraryId(libId, status, page, pageSize).then( inventory => {
            setInventory(inventory);
        });
    }, [page, status]);

    useEffect( () => {setPage(0)}, [status]) //ResetPageNum on filter change

    const handlePageChange = (event:any, value:number) => {
        setPage(value-1); //Page index starts at 0, but shown to user as 1
    }

    const handleStatusChange = (event:any) => {
        setStatus(event.target.value);
    }

    return (<div>
            <Navigation />
            <Grid container spacing={3} alignItems="center" justifyContent="center">

                <Grid item xs={10} my={2}> </Grid>
                <Grid item xs={2} my={2}>
                <FormControl sx={{ m: 1, minWidth: 80 }}>
                    <InputLabel >Availability</InputLabel>
                    <Select
                        value={status}
                        label="Availability"
                        autoWidth
                        onChange={handleStatusChange}
                    >
                        <MenuItem value={InventoryStatus.ALL}>All</MenuItem>
                        <MenuItem value={InventoryStatus.BORROWED}>Borrowed</MenuItem>
                        <MenuItem value={InventoryStatus.AVAILABLE}>Available</MenuItem>
                    </Select>
                    </FormControl>
                </Grid>
                
                <Grid item xs={12} mx={2}>
                    {libraryId && <InventoryList inventory={inventory} libraryId={libraryId}/>}
                </Grid>
                
                {inventory && 
                    <Pagination count={inventory.totalPages} color="primary" onChange={handlePageChange}/>
                }
                
            </Grid>
    
        </div>);
}

export default InventoryPage;