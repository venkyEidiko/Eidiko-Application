package com.eidiko.controller;

import com.eidiko.entity.Address;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.AddressService;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    //this api is used for updating the address of an employee by using employeeid
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<ResponseModel<Object>> updateAddress(@PathVariable Long employeeId, @RequestBody Address addressDetails) {
        try {
            Address updatedAddress = addressService.updateAddress(employeeId, addressDetails);
            return new CommonResponse<>().prepareSuccessResponseObject("Address updated successfully for "+employeeId);
        } catch (UserNotFoundException e) {
            return new CommonResponse<>().prepareFailedResponse2("Address not found for employeeId: " + employeeId);
        }
    }



    //this api is used for updating the address of an employee by using employeeid and by verifying the addresstype
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateAddressByEmployeeId(@PathVariable Integer employeeId, @RequestBody Address address) {
        try {
            Address updatedAddress = addressService.updateAddressByEmployeeIdAndType(employeeId, address);
            return new CommonResponse<>().prepareSuccessResponseObject("Address updated successfully for "+employeeId + " with " + address.getAddressType() + " address");
        } catch (UserNotFoundException e) {
            return new CommonResponse<>().prepareFailedResponse2("Address not found for employeeId: " + employeeId + " with "+ address.getAddressType()+ " address");
        }
    }

}