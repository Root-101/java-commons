package dev.root101.clean.core.app.usecase;

import dev.root101.clean.core.app.domain.DomainObject;
import dev.root101.clean.core.rest.ApiResponse;
import dev.root101.clean.core.repo.CRUDRepository;
import java.util.List;

/**
 * Delega las operaciones basicas del crud en el repo.
 *
 * @author Yo
 * @param <Domain>
 * @param <ID>
 * @param <CRUDRepo>
 */
public class DelegatedApiCRUDUseCase<Domain extends DomainObject<ID>, ID, CRUDRepo extends CRUDRepository<Domain, ID>> implements ApiCRUDUseCase<Domain, ID> {

    private final DelegatedCRUDUseCase<Domain, ID, CRUDRepo> crudUC;

    public DelegatedApiCRUDUseCase(CRUDRepo crudRepo) {
        this.crudUC = new DelegatedCRUDUseCase(crudRepo);
    }

    @Override
    public ApiResponse<Domain> create(Domain newObject) throws RuntimeException {
        return ApiResponse.buildSucces(
                crudUC.create(newObject)
        );
    }

    @Override
    public ApiResponse<Domain> edit(Domain objectToEdit) throws RuntimeException {
        return ApiResponse.buildSucces(
                crudUC.edit(objectToEdit)
        );
    }

    @Override
    public ApiResponse destroy(Domain objectToDestroy) throws RuntimeException {
        crudUC.destroy(objectToDestroy);
        return ApiResponse.buildSuccesVoid();
    }

    @Override
    public ApiResponse destroyById(ID keyId) throws RuntimeException {
        crudUC.destroyById(keyId);
        return ApiResponse.buildSuccesVoid();
    }

    @Override
    public ApiResponse<Domain> findBy(ID keyId) throws RuntimeException {
        return ApiResponse.buildSucces(
                crudUC.findBy(keyId)
        );
    }

    @Override
    public ApiResponse<List<Domain>> findAll() throws RuntimeException {
        return ApiResponse.buildSucces(
                crudUC.findAll()
        );
    }

    @Override
    public ApiResponse<Long> count() throws RuntimeException {
        return ApiResponse.buildSucces(
                crudUC.count()
        );
    }

}
