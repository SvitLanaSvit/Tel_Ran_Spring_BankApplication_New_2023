//1. AccountController
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private AccountService accountService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getAccountById(@PathVariable UUID id){
        return accountService.getAccountById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getAllAccounts(){
        return accountService.getAllAccountsActive();
    }
}

//2. AccountService
public interface AccountService {
    AccountDTO getAccountById(UUID id);
    AccountDTO getAllAccountsActive();
}

//3. AccountDTO
@Value
public class AccountDTO {
    String id;
    String name;
    String type;
    String status;
    String balance;
    String currencyCode;
    String createAt;
    String updateAt;
    String client;
}

//4. AccountRepository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountById(UUID id);
    List<Account> getAllAccountsActive();
}

//5. AccountMapper
@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toDTO(Account account);
    Account toEntity(AccountDTO accountDTO);
    List<AccountDTO> accountsToAccountsDTO(List<Account> accounts);
}

//6. AccountServiceImpl
