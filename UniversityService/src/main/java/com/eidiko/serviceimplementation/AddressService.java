package com.eidiko.serviceimplementation;

import com.eidiko.entity.Address;
import com.eidiko.exception_handler.BadRequestException;
import com.eidiko.exception_handler.UserNotFoundException;
import com.eidiko.repository.AddressRepo;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;


    //this is used for updating the address of the employee based on employeeid
    public Address updateAddress(Long employeeId, Address newAddressDetails) throws UserNotFoundException {
        List<Address> existingAddresses = addressRepo.findAddressesByEmployeeId(employeeId);

        if (!existingAddresses.isEmpty()) {
            Address existingAddress = existingAddresses.get(0);
            existingAddress.setAddressType(newAddressDetails.getAddressType());
            existingAddress.setDoorNumber(newAddressDetails.getDoorNumber());
            existingAddress.setStreetName(newAddressDetails.getStreetName());
            existingAddress.setLandmark(newAddressDetails.getLandmark());
            existingAddress.setArea(newAddressDetails.getArea());
            existingAddress.setCity(newAddressDetails.getCity());
            existingAddress.setState(newAddressDetails.getState());
            existingAddress.setPincode(newAddressDetails.getPincode());
            return addressRepo.save(existingAddress);
        } else {
            throw new UserNotFoundException("Address not found for employeeId: " + employeeId);
        }
    }






    //this is used for updating the address of an employee based on employeeid and addresstype
    public Address updateAddressByEmployeeIdAndType(Integer employeeId, Address newAddressData) throws UserNotFoundException {
        String addressType = newAddressData.getAddressType();
        Optional<Address> existingAddressOpt = addressRepo.findByEmployeeIdAndAddressType(employeeId, addressType);

        if (existingAddressOpt.isPresent()) {
            Address existingAddress = existingAddressOpt.get();
            existingAddress.setDoorNumber(newAddressData.getDoorNumber());
            existingAddress.setStreetName(newAddressData.getStreetName());
            existingAddress.setLandmark(newAddressData.getLandmark());
            existingAddress.setArea(newAddressData.getArea());
            existingAddress.setCity(newAddressData.getCity());
            existingAddress.setState(newAddressData.getState());
            existingAddress.setPincode(newAddressData.getPincode());

            return addressRepo.save(existingAddress);
        } else {
            throw new UserNotFoundException("Address with given employeeId and addressType not found.");
        }
    }
}
