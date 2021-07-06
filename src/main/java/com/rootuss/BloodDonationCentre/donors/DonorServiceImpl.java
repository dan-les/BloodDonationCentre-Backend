package com.rootuss.BloodDonationCentre.donors;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {
    private final DonorRepository donorRepository;

    @Override
    public List<Donor> getAllDonors() {
        return donorRepository.findAllReservations();
    }
}
