package dev.root101.clean.core.app.usecase;

import dev.root101.clean.core.app.domain.DomainObject;
import dev.root101.clean.core.framework.ApiResponse;
import dev.root101.clean.core.repo.CRUDRepository;
import java.util.List;

public class ApiDefaultCRUDUseCase<Domain extends DomainObject<ID>, ID, CRUDRepo extends CRUDRepository<Domain, ID>> implements ApiCRUDUseCase<Domain, ID> {

    private final DefaultCRUDUseCase<Domain, ID, CRUDRepo> crudUC;

    public ApiDefaultCRUDUseCase(CRUDRepo crudRepo) {
        this.crudUC = new DefaultCRUDUseCase(crudRepo);
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
