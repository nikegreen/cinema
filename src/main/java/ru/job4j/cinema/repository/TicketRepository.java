package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;
import java.util.List;
import java.util.Optional;

/**
 * <p>TicketRepository class. Репозиторий для Билетов (интерфейс хранилища)</p>
 * @author nikez
 * @version $Id: $Id
 */
public interface TicketRepository {
    /**
     * Список билетов купленных на киносеанс
     * @param sessionId идентификатор киносеана
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Ticket>} список всех Билетов
     */
    List<Ticket> findAllBySession(int sessionId);

    /**
     * Добавить Билет в хранилище
     * @param ticket тип {@link ru.job4j.cinema.model.Room} добавляемый Билет
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Ticket>} результат добавления:
     * Optional.Empty - Билет не добавлен иначе
     * Optional<Ticket> Билет с новым идентификатором.
     */
    Optional<Ticket> add(Ticket ticket);

    /**
     * Поиск Билета по идентификатору
     * @param id - идентификатор Билета
     * @return тип {@link java.util.Optional<ru.job4j.cinema.model.Ticket>} результат поиска
     * Optional.Empty - Билет не найден иначе
     * Optional<Ticket> найденный Билет.
     */
    Optional<Ticket> findById(int id);

    /**
     * Все билеты купленные пользователем
     * @param userId - идентификатор пользователя
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Ticket>} список всех Билетов
     */
    List<Ticket> findAllByUser(int userId);

    /**
     * Все билеты купленные пользователем на выбранный киносеанс
     * @param sessionId - идентификатор Киносеанса
     * @param userId - идентификатор пользователя
     * @return тип {@link java.util.List<ru.job4j.cinema.model.Ticket>} список всех Билетов
     */
    List<Ticket> findAllBySessionAndUser(int sessionId, int userId);
}
