package br.com.library.userprofile.core.port.in;

import br.com.library.userprofile.core.domain.entity.UserProfileEntity;
import br.com.library.userprofile.core.port.dto.UserProfileRequestDTO;

public interface UserProfileUseCase {

    UserProfileEntity createUserProfile(UserProfileRequestDTO userProfileRequestDTO);
}
