package ibf2022.day25.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.day25.model.Book;
import ibf2022.day25.model.Resv;
import ibf2022.day25.model.ResvDetails;
import ibf2022.day25.repository.BookRepo;
import ibf2022.day25.repository.ResvDetailsRepo;
import ibf2022.day25.repository.ResvRepo;

// A simulated service to test out transactions
@Service
public class ReservationService {
    @Autowired
    BookRepo bkRepo;

    @Autowired
    ResvRepo rRepo;

    @Autowired
    ResvDetailsRepo rdRepo;
    

    // Additional task that I want to work on:  Function to reserve a single book 
    // public Boolean reserveBook(Book book, String reservePersonName) {

    //     return true;
    // }


    // Function to batch reserve books
    // @Transactional is the simple way, not as detailed as below
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Boolean reserveBooks(List<Book> booksToBorrow, String reservePersonName) {
        Boolean bReservationCompleted = false;

        // 1. Check book availability by quantity
        Boolean bBooksAvailable = true;
        List<Book> libBooks = bkRepo.findAll();
        for (Book book : booksToBorrow) {
            // A java list function, to collect back the list of books to be borrowed that are available
            List<Book> filteredBooks = libBooks.stream().filter(b -> b.getId().equals(book.getId())).collect(Collectors.toList());

            if (!filteredBooks.isEmpty()){
                // get index 0 because there is only one book of that ID
                if (filteredBooks.get(0).getQuantity() == 0) {
                    bBooksAvailable = false;
                    break; // break means if any one book not available whole resv cannot proceed
                }
            } else {
                bBooksAvailable = true;
                // 2. If books available, minus quantity from the books (requires transaction - update)
                // for (int i = 0; i < filteredBooks.size(); i++) {
                // Integer bookId = filteredBooks.get(i).getId();
                // bkRepo.update(bookId);
                bkRepo.update(book.getId());
            }
        }

        if (bBooksAvailable) {
            // 3. Create reservation record (requires transaction - insert)
            Resv reservation = new Resv();
            reservation.setFullName(reservePersonName);
            reservation.setResvDate(Date.valueOf(LocalDate.now()));
            Integer reservationId = rRepo.createResv(reservation); // which is needed for step 4

            // 4. Create reservation details record (requires transaction - insert)
            for (Book book : booksToBorrow) {
                ResvDetails reservationDetails = new ResvDetails();
                reservationDetails.setBookId(book.getId());
                reservationDetails.setResvId(reservationId);
                rdRepo.createResvDetails(reservationDetails);
            }
            bReservationCompleted = true;
        }
        return bReservationCompleted;
    }
}
