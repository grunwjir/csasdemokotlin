package cz.grunwjir.csasdemo.csas.client

/**
 * The wrapper class for data responses including paging information.
 *
 * @param <T> data content
 * @author Jiri Grunwald
 */
data class CsasResponse<T>(
    val pageNumber: Int,
    val pageCount: Int,
    val pageSize: Int,
    val totalItemCount: Int,
    val items: List<T>
)