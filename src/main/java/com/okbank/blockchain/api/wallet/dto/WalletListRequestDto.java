package com.okbank.blockchain.api.wallet.dto;

import com.okbank.blockchain.common.payload.PageRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 월렛 목록 검색 요청 by jaeyeol
 */
@ToString
@Getter @Setter
public class WalletListRequestDto extends PageRequest {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    private String ownerName;

    @Builder
    public WalletListRequestDto(Integer pageNum, Integer pageSize, String orderBy,
                                LocalDate fromDate, LocalDate toDate, String ownerName) {
        super(pageNum, pageSize, orderBy);
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.ownerName = ownerName;
    }
}
