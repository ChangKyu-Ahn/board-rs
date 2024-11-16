package co.kr.board.modules.application.port.input;

import co.kr.board.modules.application.port.output.BoardManagementOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBoardInputPort {
	@Autowired protected BoardManagementOutputPort boardManagementOutputPort;
}
