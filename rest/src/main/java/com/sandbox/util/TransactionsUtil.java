package com.sandbox.util;

import com.sandbox.dto.TransactionsViewParametersDto;
import com.sandbox.model.FilterParameter;
import com.sandbox.model.SortParameter;
import com.sandbox.model.TransactionTypeParameter;

import java.util.List;
import java.util.Objects;

public class TransactionsUtil {

    public static TransactionsViewParametersDto buildParameters(SortParameter sortBy, Long page, Long size,
                                                                TransactionTypeParameter transactionsType,
                                                                List<FilterParameter> filterBy, Long walletId) {
        TransactionsViewParametersDto searchParameters = new TransactionsViewParametersDto();
        switch (sortBy) {
            case DATEDESC -> searchParameters.setSortParameter("datedesc");
            case DATEASC -> searchParameters.setSortParameter("dateasc");
            case AMOUNTASC -> searchParameters.setSortParameter("amountasc");
            case AMOUNTDESC -> searchParameters.setSortParameter("amountdesc");
        }
        searchParameters.setPage(Objects.requireNonNullElse(page, 0L));
        searchParameters.setSize(Objects.requireNonNullElse(size, 10L));
        String transactionTypeValue = (transactionsType != null) ? transactionsType.getValue() : null;
        searchParameters.setTransactionType(transactionTypeValue);
        List<String> filterParameters = (filterBy != null) ?
                filterBy.stream().map(FilterParameter::getValue).toList() : null;
        searchParameters.setFilterBy(filterParameters);
        searchParameters.setWalletId(walletId);

        return searchParameters;
    }
}
