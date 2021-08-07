package cz.grunwjir.csasdemo.csas.client;

import lombok.Data;

import java.util.List;

/**
 * The wrapper class for data responses including paging information.
 *
 * @param <T> data content
 * @author Jiri Grunwald
 */
@Data
public class CsasResponse<T> {

    private Integer pageNumber;
    private Integer pageCount;
    private Integer pageSize;
    private Integer totalItemCount;
    private List<T> items;
}