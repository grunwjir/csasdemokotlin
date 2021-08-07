package cz.grunwjir.csasdemo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * The wrapper class for data responses including paging information.
 *
 * @param <T> data content
 * @author Jiri Grunwald
 */
@Getter
@Builder
public class Page<T> {

    private final Integer pageNumber;
    private final Integer pageCount;
    private final Integer pageSize;
    private final Integer totalItemCount;
    private final List<T> items;
}