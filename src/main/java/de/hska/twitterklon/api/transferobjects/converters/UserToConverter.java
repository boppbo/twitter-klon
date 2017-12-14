package de.hska.twitterklon.api.transferobjects.converters;

import java.util.List;
import java.util.stream.Collectors;

import de.hska.twitterklon.api.transferobjects.UserTO;

public class UserToConverter {

    public static List<UserTO> fromStringList(List<String> userTOStrings) {
        return userTOStrings.stream().map(UserTO::new).collect(Collectors.toList());
    }
}
