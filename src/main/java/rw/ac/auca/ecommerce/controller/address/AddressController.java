package rw.ac.auca.ecommerce.controller.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.address.model.Address;
import rw.ac.auca.ecommerce.core.address.service.IAddressService;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/address/")
public class AddressController {

    private final IAddressService addressService;


    @GetMapping({"/", "search/all"})
    public String getAllAddresses(Model model) {
        model.addAttribute("address", new Address());
        List<Address> addresses = addressService.findAddessByState(Boolean.TRUE);
        model.addAttribute("addresses", addresses);
        model.addAttribute("provinceState", EProvinceState.values());
        model.addAttribute("districtState", EDistrictState.values());
        return "customer/addresseP";
    }

    @GetMapping("/register")
    public String registerAddress(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("provinceState", EProvinceState.values());
        model.addAttribute("districtState", EDistrictState.values());
        return "customer/addresseP";
    }

    @PostMapping("/register")
    public String registerAddress(@ModelAttribute Address theAddress, Model model) {
        try {
            if (Objects.nonNull(theAddress)) {
                addressService.registerAddress(theAddress);
                model.addAttribute("message", "ADDRESS has been registered successfully");
                return "redirect:/address/";
            } else {
                model.addAttribute("error", "Data is invalid");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error Registering ADDRESS: " + e.getMessage());
        }
        model.addAttribute("address", new Address());
        model.addAttribute("addresses", addressService.findAddessByState(Boolean.TRUE));
        model.addAttribute("provinceState", EProvinceState.values());
        model.addAttribute("districtState", EDistrictState.values());
        return "customer/addresseP";
    }

    @PostMapping("/delete")
    public String deleteAddress(@RequestParam("id") String id, Model model) {
        if (Objects.nonNull(id)) {
            Address theAddress = new Address();
            theAddress.setId(UUID.fromString(id));
            addressService.deleteAddress(theAddress);
        }
        return "redirect:/address/search/all";
    }

    @PostMapping("/update")
    public String updateRole(@RequestParam("id") String id, Model model) {
        if (Objects.nonNull(id)) {
            Address theAddress = addressService.findAddressByIdAndState(UUID.fromString(id), Boolean.TRUE);
            if (Objects.nonNull(theAddress)) {
                model.addAttribute("address", theAddress);
                return "customer/addresseP";
            }
        }
        model.addAttribute("error", "Wrong Information");
        return "customer/addresseP";
    }

    @GetMapping("/districts-by-province")
    @ResponseBody
    public List<EDistrictState> getDistrictsByProvince(@RequestParam EProvinceState province) {
        return Arrays.stream(EDistrictState.values())
                .filter(d -> d.getProvince() == province)
                .collect(Collectors.toList());
    }
}