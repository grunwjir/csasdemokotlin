package cz.grunwjir.csasdemo


/**
 * The wrapper class for data responses including paging information.
 *
 * @param <T> data content
 * @author Jiri Grunwald
</T> */
data class Page<T>(val pageNumber: Int,
                   val pageCount: Int,
                   val pageSize: Int,
                   val totalItemCount: Int,
                   val items: List<T>)